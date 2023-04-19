import '../styles/SearchCar.css'
import React, { useRef } from 'react';

export default function SearchCar() {
    const sectionRef = useRef(null);

    const scrollToSearchCar = () => {
        console.log('function called');
        sectionRef.current.scrollIntoView({ behavior: 'smooth' });
    };

    return (
        <div id="search-car" ref={sectionRef}>
            <div id="search-car-container">
                <div id="search-car-title">
                    Find the car of your dreams
                </div>
                <div id="search-car-filters">

                </div>
                <div id="display-car-list">
                    
                </div>
            </div>
        </div>
    );
}