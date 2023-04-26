import './ResetPasswordPage.css';
import Footer from '../home-page/components/Footer.js';
import NavigationBar from '../home-page/components/NavigationBar.js';
import InputForm from '../login-page/InputForm.js';
import { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';

function ResetPasswordPage() {
    const navigate = useNavigate();
    const [password, setPassword] = useState('');
    const [repeatedPassword, setRepeatedPassword] = useState('');

    const queryParams = new URLSearchParams(window.location.search);
    const username = queryParams.get("username");
    const token = queryParams.get("token");

    useEffect(() => {
        document.title = "4 Wheels Drive";
    
        if (username === null || token === null) {
          navigate('/');
          return;
        }
    }, []);

    function handleResetPassword() {
        if (password === repeatedPassword) {
            const data = {
                'password': password
            }

            axios.post('http://localhost:8080/reset-password/reset?token=' + token + '&username=' + username, data)
            .then(response => {
                toast.success(response.data.message);
            })
            .catch(error => {
                toast.error(error.response.data.message);
            });
        } else {
            toast.error('Passwords do not match.');
        }
    }

    return (
        <div className='reset-password-container'>
            <ToastContainer/>
            <NavigationBar/>
            <div className='reset-password-background'>
                <div className='reset-password-form'>
                    Enter your new password:
                    <InputForm type='password' placeholder='Enter new password' value={password} setValue={setPassword}/>
                    <InputForm type='password' placeholder='Repeat the password' value={repeatedPassword} setValue={setRepeatedPassword}/>
                    <div className='reset-password-button' onClick={handleResetPassword}>Submit</div>
                </div>
            </div>
            <Footer/>
        </div>
    );
}

export default ResetPasswordPage;