import '../styles/NavigationBar.css'
import { useState, useEffect } from "react";
import { useNavigate } from 'react-router-dom';
import Cookies from 'js-cookie';

export default function NavigationBar(props) {
    const [isScrolled, setScrolled] = useState(false);
    const navigate = useNavigate();
    const token = Cookies.get('authToken')  || null;
    console.log(token);
    useEffect(() => {
        function handleScroll() {
            const scrollTop = document.documentElement.scrollTop;

            if (scrollTop === 0) {
                setScrolled(false);
            } else {
                setScrolled(true);
            }
        }

        window.addEventListener("scroll", handleScroll);
        return () => window.removeEventListener("scroll", handleScroll);
    }, []);

    function handleHomeButton() {
        const bgContainer = document.getElementById('bg-container');
        if (bgContainer) {
            const elementTop = bgContainer.getBoundingClientRect().top;
            window.scrollBy({
                top: elementTop,
                behavior: 'smooth'
            })
        }
    }

    function handleMyProfileButton() {
        navigate('/');
        setTimeout(() => {
            const myProfileContainer = document.getElementById('my-profile-container');
            if (myProfileContainer) {
                const elementTop = myProfileContainer.getBoundingClientRect().top;
                window.scrollBy({
                    top: elementTop - 80,
                    behavior: 'smooth'
                })
            }
        }, 100); 
    }

    function handleLogOutButton() {
        Cookies.remove('authToken');
        window.location.reload();
    }

    return (
       <div className={`navigation-bar ${isScrolled ? "scrolled" : ""}`}>
            <div className='logo-container'>
                <div className='logo' onClick={() => {
                    handleHomeButton();
                    navigate('/');
                }}>4 Wheels Drive</div>
            </div>
            <div className='buttons-container'>
                {token !== null && (<div className="button-style" onClick={handleLogOutButton}>Log out</div>)}
                {token === null ?
                    (<div className="button-style">Register</div>) : 
                    (<div className="button-style" onClick={handleMyProfileButton}>My Profile</div>)
                }
                {token === null ? 
                    (<div className="button-style" onClick={() => navigate('/login')}>Login</div>) : 
                    (<div className="button-style" onClick={() => navigate('/vehicles/sell')}>Sell</div>)}
                <div className="button-style">Contact</div>
                <div className="button-style">About us</div>
                <div className="button-style" onClick={() => {
                    handleHomeButton();
                    navigate('/');
                }}>Home</div>
            </div>
        </div>
    );
}