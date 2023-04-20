import '../../styles/SearchCar.css'
import React, { useRef, useState } from 'react';
import CarScroller from './CarScroller';
import FilterSection from './FilterSection';
import axios from 'axios';

export default function SearchCar() {
    const options = [
        { id: 1, name: "Option 1"}, 
        { id: 2, name: "Option 2"}, 
        { id: 3, name: "Option 3"}
    ]; //tomodify

    const [brandSelected, setBrandSelected] = useState(null);
    const [modelSelected, setModelSelected] = useState(null);
    const [generationSelected, setGenerationSelected] = useState(null);
    const [minPrice, setMinPrice] = useState('');
    const [maxPrice, setMaxPrice] = useState('');
    const [minYear, setMinYear] = useState('');
    const [maxYear, setMaxYear] = useState('');

    function handleFilterClick() {
        const data = {
            'brandSelected': brandSelected,
            'modelSelected': modelSelected,
            'generationSelected': generationSelected,
            'minPrice': minPrice,
            'maxPrice': maxPrice,
            'minYear': minYear,
            'maxYear': maxYear
        };

        console.log(data);
    }

    return (
        <div id="search-car">
            <div id="search-car-container">
                <div id="search-car-title">
                    Find the car of your dreams
                </div>
                <FilterSection brand={[brandSelected, setBrandSelected]} 
                               model={[modelSelected, setModelSelected]} 
                               generation={[generationSelected, setGenerationSelected]}
                               minPrice={[minPrice, setMinPrice]}
                               maxPrice={[maxPrice, setMaxPrice]}
                               minYear={[minYear, setMinYear]}
                               maxYear={[maxYear, setMaxYear]}
                               handleClick={handleFilterClick}
                               options={options}/>
                <CarScroller/>
            </div>
        </div>
    );
}