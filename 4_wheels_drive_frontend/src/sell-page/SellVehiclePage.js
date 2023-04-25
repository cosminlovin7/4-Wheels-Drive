import './SellVehiclePage.css'
import { useState, useEffect } from 'react';
import Cookies from 'js-cookie';
import axios from 'axios';
import Footer from '../home-page/components/Footer.js';
import NavigationBar from '../home-page/components/NavigationBar.js';
import { ToastContainer, toast } from 'react-toastify';


function SellVehiclePage() {
    let brandModels = [];
    let modelGenerations = [];
    let fuelTypes = ['GAS', 'GASGPL', 'DIESEL', 'ELECTRIC', 'HYBRID'];

    const token = Cookies.get('authToken') || null;
    const [selectedBrand, setSelectedBrand] = useState('');
    const [selectedModel, setSelectedModel] = useState('');
    const [selectedGeneration, setSelectedGeneration] = useState('');
    const [brands, setBrands] = useState([]);
    const [models, setModels] = useState([]);
    const [generations, setGenerations] = useState([]);
    const [price, setPrice] = useState('');
    const [year, setYear] = useState('');
    const [fuelType, setFuelType] = useState('');

    const handleBrandChange = (event) => {
        setSelectedBrand(event.target.value);
        if (event.target.value === '') {
            setSelectedModel('');
            setSelectedGeneration('');
        }
    };

    const handleModelChange = (event) => {
        setSelectedModel(event.target.value);
        if (event.target.value === '') {
            setSelectedGeneration('');
        }
    }

    const handleGenerationChange = (event) => {
        setSelectedGeneration(event.target.value);
    }

    const handlePriceChange = (event) => {
        setPrice(event.target.value);
    }

    const handleYearChange = (event) => {
        if (event.target.value < 0 || event.target.value > 3000)
            toast.error('Invalid year.');
        else 
            setYear(event.target.value);
    }

    const handleFuelTypeChange = (event) => {
        setFuelType(event.target.value);
    }

    console.log(year);

    useEffect(() => {
        document.title = "4 Wheels Drive";
        
        axios.get(process.env.REACT_APP_GET_BRANDS_URL)
        .then(response => {
            setBrands(response.data.brands);
        })
        .catch(error => {
            console.log(error);
        });

        axios.get(process.env.REACT_APP_GET_ALL_MODELS_URL)
        .then(response => {
            setModels(response.data);
        })
        .catch(error => {
            console.log(error);
        });

        axios.get(process.env.REACT_APP_GET_ALL_GENERATIONS_URL)
        .then(response => {
            setGenerations(response.data);
        })
        .catch(error => {
            console.log(error);
        });
    }, []);

    if (selectedBrand !== '') {
        models.map((model) => {
            if (model['brandName'] === selectedBrand) {
                brandModels.push(model);
            }
        });
    }

    if (selectedModel !== '') {
        generations.map((generation) => {
            if (generation['modelName'] === selectedModel) {
                modelGenerations.push(generation);
            }
        })
    }

    function convertToBase64(e) {
        const file = e.target.files[0];
        console.log(file);
        const reader = new FileReader();
    
        let base64Img = '';
        
        reader.onloadend = () => {
            base64Img = reader.result.toString();
    
            console.log('base64Img: ' + base64Img);
        }
    
        reader.readAsDataURL(file);
    }

    function handleSubmitButton() {
        console.log('brandSelected: ' + selectedBrand + ' modelSelected ' + selectedModel + ' generationSelected ' + selectedGeneration);
    }

    return (
        <div className='sell-page'>
            <NavigationBar/>
            <ToastContainer/>
            <div className='sell-page-container'>
                <div className='sell-details-container'>
                    <div className='sell-container-title'>Details</div>
                    <div className='brand-selector'>
                        <label className='brand-label'>Brand</label>
                        <select id="sell-brand-select" className='sell-page-select' value={selectedBrand} onChange={handleBrandChange}>
                            <option key='' className='sell-page-option' value=''>Select a brand...</option>
                            {brands.map((brand) => {
                                return (
                                    <option key={brand['id']} className='sell-page-option' value={brand['brandName']}>{brand['brandName']}</option>
                            )})}
                        </select>
                    </div>

                    <div className='model-selector'>
                        <label className='model-label'>Model</label>
                        <select id="sell-model-select" className='sell-page-select' value={selectedModel} onChange={handleModelChange}>
                            <option key='' className='sell-page-option' value=''>Select a model...</option>
                            {brandModels.map((model) => {
                                return (
                                    <option key={model['id']} className='sell-page-option' value={model['modelName']}>{model['modelName']}</option>
                            )})}
                        </select>
                    </div>

                    <div className='generation-selector'>
                        <label className='generation-label'>Generation</label>
                        <select id="sell-generation-select" className='sell-page-select' value={selectedGeneration} onChange={handleGenerationChange}>
                            <option key='' className='sell-page-option' value=''>Select a generation...</option>
                            {modelGenerations.map((generation) => {
                                return (
                                    <option key={generation['id']} className='sell-page-option' value={generation['generationName']}>{generation['generationName']}</option>
                            )})}
                        </select>
                    </div>

                    <div className='price-selector'>
                        <label className='price-label'>Price</label>
                        <input id="sell-price-input" type="number" className='sell-numeric-input' value={price} onChange={handlePriceChange}/>
                    </div>

                    <div className='year-selector'>
                        <label className='year-label'>Year</label>
                        <input id="sell-year-input" type="number" className='sell-numeric-input' value={year} onChange={handleYearChange}/>
                    </div>

                    <div className='fuel-types-selector'>
                        <label className='fuel-types-label'>Fuel Type</label>
                        <select id="sell-fuel-types-select" className='sell-page-select' value={fuelType} onChange={handleFuelTypeChange}>
                            <option key='' className='sell-page-option' value=''>Select a fuel type...</option>
                            {fuelTypes.map((fuelType) => {
                                return (
                                    <option key={fuelType} className='sell-page-option' value={fuelType}>{fuelType}</option>
                            )})}
                        </select>
                    </div>
                </div>
                <div className='sell-body-container'>
                    <div className='sell-container-title'>Body</div>
                </div>
                <div className='sell-engine-container'>
                    <div className='sell-container-title'>Engine</div>
                </div>
                <div className='sell-options-container'>
                    <div className='sell-container-title'>Extra-Options</div>
                </div>
                <div className='upload-photo-button-container'>
                    <label for="photoupload" className="upload-photo-button">
                        Upload Photo
                    </label>
                    <input 
                           name='Upload Photo' 
                           id="photoupload" 
                           type="file" 
                           accept=".jpg, .jpeg, .png"
                           onChange={e => convertToBase64(e)}
                           style={{display: 'none'}}/> 
                </div>
                <div className='submit-button-container'>
                    <div className='submit-button' onClick={handleSubmitButton}>Submit</div>
                </div>
            </div>
            <Footer/>

        </div>
        // <>
        //     {token !== null ? 
        //         (<div>Sell page</div>) 
        //         :
        //         (<div>You are not authorized to see this page :(</div>)
        //     }
        // </>
    );
}

export default SellVehiclePage;