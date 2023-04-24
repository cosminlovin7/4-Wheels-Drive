import '../../styles/FilterSection.css';
import Dropdown from './Dropdown.js';
import DataInput from './DataInput.js';
import FilterButton from './FilterButton.js';
import React, { useState } from 'react';

export default function FilterSection(params) {
    const [brandSelected, setBrandSelected] = params.brand;
    const [brandActive, setBrandActive] = useState(false);
    const [modelSelected, setModelSelected] = params.model;
    const [modelActive, setModelActive] = useState(false);
    const [generationSelected, setGenerationSelected] = params.generation;
    const [generationActive, setGenerationActive] = useState(false);
    const [minPrice, setMinPrice] = params.minPrice;
    const [maxPrice, setMaxPrice] = params.maxPrice;
    const [minYear, setMinYear] = params.minYear;
    const [maxYear, setMaxYear] = params.maxYear;

    var buttonStates = {
        'Brand': [brandActive, setBrandActive],
        'Model': [modelActive, setModelActive],
        'Generation': [generationActive, setGenerationActive]
    }

    var buttonSelected = {
        'Brand': [brandSelected, setBrandSelected],
        'Model': [modelSelected, setModelSelected],
        'Generation': [generationSelected, setGenerationSelected]
    }

    return (
        <div id="filter-container">
            <div id="filter-section">
                <Dropdown name="Brand" 
                          options={params.brands} 
                          otherActive={true}
                          buttonStates={buttonStates}
                          buttonSelected={buttonSelected}/>
                <Dropdown name="Model" 
                          options={params.models} 
                          otherActive={brandSelected}
                          buttonStates={buttonStates}
                          buttonSelected={buttonSelected}/>
                <Dropdown name="Generation" 
                          options={params.generations} 
                          otherActive={modelSelected}
                          buttonStates={buttonStates}
                          buttonSelected={buttonSelected}/>
                <DataInput defaultText="Min. Price" value={minPrice} setValue={setMinPrice}/>
                <DataInput defaultText="Max. Price" value={maxPrice} setValue={setMaxPrice}/>
                <DataInput defaultText="Min. Year" value={minYear} setValue={setMinYear}/>
                <DataInput defaultText="Max. Year" value={maxYear} setValue={setMaxYear}/>
                <FilterButton name="Filter" handleClick={params.handleClick} buttonStates={buttonStates}/>
            </div>
        </div>
    );
}