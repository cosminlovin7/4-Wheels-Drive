import '../styles/FrontSlider.css'
import FrontSliderButton from './FrontSliderButton';
import { convertToBase64 } from './Utils.js';

export default function FrontSlider() {
    function scrollToSearchCar() {
        const searchCarContainer = document.getElementById('search-car-container');
        if (searchCarContainer) {
            const elementTop = searchCarContainer.getBoundingClientRect().top;
            window.scrollBy({
                top: elementTop - 80 - 50,
                behavior: 'smooth'
            })
        }
    }

    return (
        <div className="frontslider-container">
            <span>Luxury. Elegance. Comfort.</span>
            <div id="frontslider-buttons">
                <FrontSliderButton buttonName="Search" handleClick={scrollToSearchCar}/>
                <FrontSliderButton buttonName="About Us"/>
                {/* <input id="fileupload" 
                       type="file" 
                       accept=".jpg, .jpeg, .png"
                       onChange={e => convertToBase64(e)}/> */}
            </div>
        </div>
    );
}