import '../styles/FrontSlider.css'
import FrontSliderButton from './FrontSliderButton';

export default function FrontSlider() {
    function scrollToSearchCar() {
        const searchCarContainer = document.getElementById('search-car-container');
        if (searchCarContainer) {
            const elementTop = searchCarContainer.getBoundingClientRect().top;
            window.scrollBy({
                top: elementTop - 60,
                behavior: 'smooth'
            })
        }
    }

    return (
        <div className="frontslider-container">
            <span>Luxury. Elegance. Comfort.</span>
            <br />
            <FrontSliderButton handleClick={scrollToSearchCar} />
            <FrontSliderButton/>
        </div>
    );
}