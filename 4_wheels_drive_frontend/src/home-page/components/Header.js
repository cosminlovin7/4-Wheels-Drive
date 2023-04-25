import '../styles/Header.css'
import "typeface-montserrat";
import NavigationBar from './NavigationBar';
import FrontSlider from './FrontSlider';

export default function Header(props) {
    return (
        <div className="header-container">
            <div id="bg-container" className="background-container">
                <NavigationBar/>
                <FrontSlider/>
            </div>
        </div>
    );
}