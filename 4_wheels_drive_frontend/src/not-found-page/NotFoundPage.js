import './NotFoundPage.css';
import Footer from '../home-page/components/Footer.js';
import NavigationBar from '../home-page/components/NavigationBar.js';

function NotFoundPage(props) {
    return (
        <div className='not-found-container'>
            <NavigationBar/>
            <div className='not-found-background'>
                <div className='error-text-container'>
                    <div className='error-text'>Sorry, the page you were looking for could not be found.</div>
                </div>
            </div>
            <Footer/>
        </div>
    );
}

export default NotFoundPage;