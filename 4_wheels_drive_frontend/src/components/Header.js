import '../styles/Header.css'
import "typeface-montserrat";
import NavigationBar from './NavigationBar';
import FrontSlider from './FrontSlider';

export default function Header() {
    return (
        <div className="header-container">
            <div className="background-container">
                <NavigationBar/>
                <FrontSlider/>
            </div>
        </div>
    );
}