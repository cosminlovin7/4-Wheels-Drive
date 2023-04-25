import '../../styles/SearchCar.css'
import React, { useRef, useState } from 'react';
import CarScroller from './CarScroller';
import FilterSection from './FilterSection';
import ViewCarInfo from './ViewCarInfo';

let filters = null;

export default function SearchCar(props) {
    let brandModels = [];
    let modelGenerations = [];

    const [brandSelected, setBrandSelected] = useState({'id': null, 'name': null});
    const [modelSelected, setModelSelected] = useState({'id': null, 'name': null});
    const [generationSelected, setGenerationSelected] = useState({'id': null, 'name': null});
    const [minPrice, setMinPrice] = useState('');
    const [maxPrice, setMaxPrice] = useState('');
    const [minYear, setMinYear] = useState('');
    const [maxYear, setMaxYear] = useState('');
    const [applyFilters, setApplyFilters] = useState(false);
    const [vehicle, setVehicle] = useState(null);

    function handleFilterClick() {
        if (brandSelected['name'] !== null || modelSelected['name'] !== null || generationSelected['name'] !== null || 
            minPrice !== '' || maxPrice !== '' || minYear !== '' || maxYear !== '') 
            filters = {
                'brandSelected': brandSelected,
                'modelSelected': modelSelected,
                'generationSelected': generationSelected,
                'minPrice': minPrice,
                'maxPrice': maxPrice,
                'minYear': minYear,
                'maxYear': maxYear
            };
        else
            filters = null;
        
        setApplyFilters(!applyFilters);

        console.log(filters);
    }
    
    if (brandSelected['name'] !== null) {
        props.models.map((model) => {
            if (model['brandName'] === brandSelected['name']) {
                brandModels.push(model);
            }
        });
    }
    
    if (modelSelected['name'] !== null) {
        props.generations.map((generation) => {
            if (generation['modelName'] === modelSelected['name']) {
                modelGenerations.push(generation);
            }
        })
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
                               brands={props.brands}
                               models={brandModels}
                               generations={modelGenerations}/>
                <CarScroller filters={filters} 
                             applyFilters={applyFilters} 
                             setVehicle={setVehicle} />
                <ViewCarInfo id='car-info-container' vehicle={vehicle}/>
            </div>
        </div>
    );
}