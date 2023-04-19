import '../styles/SearchCar.css'
import React, { useRef } from 'react';

export default function SearchCar() {
    const sectionRef = useRef(null);

    const scrollToSearchCar = () => {
        console.log('function called');
        sectionRef.current.scrollIntoView({ behavior: 'smooth' });
    };

    return (
        <div id="search-car-container" ref={sectionRef}>
            This is the section to search cars.
        </div>
    );
}