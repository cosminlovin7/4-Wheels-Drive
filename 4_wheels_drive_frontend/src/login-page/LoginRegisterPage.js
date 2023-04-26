import './LoginRegisterPage.css'
import { useState } from 'react';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import InputForm from './InputForm.js';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import Cookies from 'js-cookie';

function LoginRegisterPage(props) {
    const [loginSelected, setLoginSelected] = useState(props.loginSelected);
    const [registerSelected, setRegisterSelected] = useState(props.registerSelected);
    const navigate = useNavigate();

    const [loginUsername, setLoginUsername] = useState('');
    const [loginPassword, setLoginPassword] = useState('');
    const [registerUsername, setRegisterUsername] = useState('');
    const [registerEmail, setRegisterEmail] = useState('');
    const [registerPassword, setRegisterPassword] = useState('');

    function handleLoginClick() {
        if (loginSelected === true) {
            if (loginUsername === '' || loginPassword === '') {
                toast.error('Please, fill the username and password fields!');
                return;
            } else {
                const request = {
                    'username': loginUsername,
                    'password': loginPassword
                };

                axios.post(process.env.REACT_APP_LOGIN_URL, request)
                .then(response => {
                    toast.success(response.data.message);
                    Cookies.set('authToken', response.data.token);
                    navigate('/');
                })
                .catch(error => {
                    switch(error.response.data.status) {
                        case(401):
                            if (error.response.data.message === 'The account is not activated.') {
                                toast.error(error.response.data.message);
                                axios.get('http://localhost:8080/register/resend?username=' + loginUsername)
                                    .then(response => {
                                        toast.success(response.data.message);
                                    })
                                    .catch(error => {
                                        toast.error(error.response.data.message);
                                    })
                            } else {
                                toast.error(error.response.data.message);
                            }
                            break;
                        default:
                            toast.error(error.response.data.message);
                            break;
                    }
                });
            }
        }
    }

    function handleRegisterClick() {
        if (registerSelected === true) {
            if (registerUsername === '' || registerEmail === '' || registerPassword === '') {
                toast.error('Please, fill the username, email and password fields!');
                return;
            } else {
                const request = {
                    'username': registerUsername,
                    'email': registerEmail,
                    'password': registerPassword
                };

                axios.post('http://localhost:8080/register', request)
                .then(response => {
                    toast.success(response.data.message);
                    setRegisterSelected(false);
                    setRegisterUsername('');
                    setRegisterEmail('');
                    setRegisterPassword('');
                    setLoginSelected(true);
                })
                .catch(error => {
                    toast.error(error.response.data.message);
                });
            }
        }
    }

    function handleForgotPassword() {
        if (loginUsername !== '') {
            axios.get('http://localhost:8080/reset-password?username=' + loginUsername)
            .then(response => {
                toast.success(response.data.message);
            })
            .catch(error => {
                toast.error(error.response.data.message);
            });
        } else {
            toast.error('Please provide the username.');
        }
    }

    function handleLoginContainerClick() {
        setLoginSelected(true);
        setRegisterSelected(false);
        setRegisterUsername('');
        setRegisterEmail('');
        setRegisterPassword('');
    }

    function handleRegisterContainerClick() {
        setLoginSelected(false);
        setRegisterSelected(true);
        setLoginUsername('');
        setLoginPassword('');
    }

    return (
        <div className='login-register-container'>
            <ToastContainer />
            <div className={registerSelected === true ? 'container not-selected' : 'container'} onClick={handleLoginContainerClick}>
                <div className='login-container'>
                    <div className='text-container'>
                        <div className='login-text'>Log in</div>
                        <div className='logo-text'>Find the car of your dreams</div>
                    </div>
                    <div className="login-form">
                        <div className='username-text'>Username</div>
                        <InputForm type='text' placeholder='Enter username' value={loginUsername} setValue={setLoginUsername}/>
                        <div className='password-text'>Password</div>
                        <InputForm type='password' placeholder="Enter password" value={loginPassword} setValue={setLoginPassword}/>
                    </div>
                    <div className="button" onClick={handleLoginClick}>
                        Log In
                    </div>
                    <div className="forgot-password-button" onClick={handleForgotPassword}>
                        Forgot your password?
                    </div>
                </div>
            </div>
            <div className={loginSelected === true ? 'container not-selected' : 'container'} onClick={handleRegisterContainerClick}>
                <div className='register-container'>
                    <div className='rtext-container'>
                        <div className='login-text'>Register</div>
                        <div className='logo-text'>Place your car advert easier than ever</div>
                    </div>
                    <div className="login-form">
                        <div className='username-text'>Username</div>
                        <InputForm type='text' placeholder="Enter username" value={registerUsername} setValue={setRegisterUsername}/>
                        <div className='email-text'>Email</div>
                        <InputForm type='email' placeholder="Enter email" value={registerEmail} setValue={setRegisterEmail}/>
                        <div className='password-text'>Password</div>
                        <InputForm type='password' placeholder="Enter password" value={registerPassword} setValue={setRegisterPassword}/>
                    </div>
                    <div className="button" onClick={handleRegisterClick}>
                        Register
                    </div>
                </div>
            </div>
        </div>
    );
}

export default LoginRegisterPage;