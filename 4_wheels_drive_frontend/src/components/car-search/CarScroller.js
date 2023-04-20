import {data} from '../../mockData'
import '../../styles/CarScroller.css'
import { MdChevronLeft, MdChevronRight } from 'react-icons/md';

export default function CarScroller() {

    const slideLeft = () => {
        var slider = document.getElementById("scroller-container")
        slider.scrollLeft = slider.scrollLeft - 500;
    }

    const slideRight = () => {
        var slider = document.getElementById("scroller-container");
        slider.scrollLeft = slider.scrollLeft + 500;
    }

    return (
        <div id="car-list-container">
            <MdChevronLeft className="sliders" size={40} onClick={slideLeft}/>
            <div id="scroller-container">
                {
                    data.map((item) => (
                        <div key={item.id} className="ad-box">
                            <img className="ad-image" src={item.img} alt='Image'/>
                            <div className="car-info">
                                <div className="car-name">
                                    <span id="brand">{item.brand}</span>
                                    <span id="model">{item.model}</span>
                                    <span id="generation">{item.generation}</span>
                                </div> 
                                <div className="info-container">
                                    <div className="info-title">
                                        <span className="title-text">Price: </span>
                                    </div>
                                    <span id="price">{item.price}$</span>
                                </div>
                                <div className="info-container">
                                    <div className="info-title">
                                        <span className="title-text">KM: </span>
                                    </div>
                                    <span id="kilometrage">{item.kilometrage} KM</span>
                                </div>
                                <div className="info-container">
                                    <div className="info-title">
                                        <span className="title-text">Year: </span>
                                    </div> 
                                    <span id="year">{item.year}</span>
                                </div>
                                <div className="info-container">
                                    <div className="info-title">
                                        <span className="title-text">Country: </span>
                                    </div>
                                    <span id="country">{item.country}</span>
                                </div>
                                <div className="info-container">
                                    <div className="info-title">
                                        <span className="title-text">Fuel Type: </span>
                                    </div>
                                    <span id="fuelType">{item.fuelType}</span>
                                </div>
                            </div>
                        </div> 
                    ))
                }
            </div>
            <MdChevronRight className="sliders" size={40} onClick={slideRight}/>
        </div>
    );
}