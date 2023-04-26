import './SellVehiclePage.css'
import { useState, useEffect } from 'react';
import Cookies from 'js-cookie';
import axios from 'axios';
import Footer from '../home-page/components/Footer.js';
import NavigationBar from '../home-page/components/NavigationBar.js';
import LoginRegisterPage from '../login-page/LoginRegisterPage.js'
import { ToastContainer, toast } from 'react-toastify';
import { useNavigate } from 'react-router-dom';

function SellVehiclePage() {
    let brandModels = [];
    let modelGenerations = [];
    let fuelTypes = ['GAS', 'GASGPL', 'DIESEL', 'ELECTRIC', 'HYBRID'];
    let bodyTypes = ['CONVERTIBLE', 'COMBI', 'HATCHBACK', 'COUPE', 'SEDAN', 'SUV'];
    let colorTypes = ['WHITE', 'BLUE', 'GREEN', 'BLACK', 'RED', 'PURPLE', 'YELLOW', 'ORANGE', 'GREY'];
    let nrSeats = [1, 2, 3, 4, 5, 6, 7, 8];
    let emissionLevelTypes = ['EURO_1', 'EURO_2', 'EURO_3', 'EURO_4', 'EURO_5', 'EURO_6', 'ZERO_EMISSION'];
    let drivetrainTypes = ['FWD', 'RWD', 'AWD'];
    let transmissionTypes = ['MANUAL', 'AUTOMATIC'];
    let cylinderCapacities = Array.from({ length: 63 }, (_, index) => ((index * 0.1) + 0.8).toFixed(1));

    const [selectedBrand, setSelectedBrand] = useState('');
    const [selectedModel, setSelectedModel] = useState('');
    const [selectedGeneration, setSelectedGeneration] = useState('');
    const [brands, setBrands] = useState([]);
    const [models, setModels] = useState([]);
    const [generations, setGenerations] = useState([]);
    const [price, setPrice] = useState('');
    const [year, setYear] = useState('');
    const [km, setKm] = useState('');
    const [fuelType, setFuelType] = useState('');
    const [bodyType, setBodyType] = useState('');
    const [colorType, setColorType] = useState('');
    const [nrOfSeats, setNrOfSeats] = useState('');
    const [emissionLevelType, setEmissionLevelType] = useState('');
    const [horsePower, setHorsePower] = useState('');
    const [cylinderCapacity, setCylinderCapacity] = useState('');
    const [transmissionType, setTransmissionType] = useState('');
    const [drivetrainType, setDrivetrainType] = useState('');

    const [hasAppleCarPlay, setHasAppleCarPlay] = useState(false);
    const [hasHeadUpDisplay, setHasHeadUpDisplay] = useState(false);
    const [hasAndroid, setHasAndroid] = useState(false);
    const [hasBluetooth, setHasBluetooth] = useState(false);
    const [hasAirConditioning, setHasAirConditioning] = useState(false);
    const [hasDualClimatic, setDualClimatic] = useState(false);
    const [hasPanoramicRoof, setHasPanoramicRoof] = useState(false);
    const [hasSunRoof, setHasSunRoof] = useState(false);
    const [hasElectricDriverSeat, setHasElectricDriverSeat] = useState(false);
    const [hasElectricPassengerSeat, setHasElectricPassengerSeat] = useState(false);
    const [hasElectricSeats, setHasElectricSeats] = useState(false);
    const [hasHeatedDriverSeat, setHasHeatedDriverSeat] = useState(false);
    const [hasHeatedPassengerSeat, setHasHeatedPassengerSeat] = useState(false);
    const [hasHeatedSteeringWheel, setHasHeatedSteeringWheel] = useState(false);
    const [hasHeatedWindscreen, setHasHeatedWindscreen] = useState(false);
    const [hasCruiseControl, setHasCruiseControl] = useState(false);
    const [hasDistanceControl, setHasDistanceControl] = useState(false);
    const [has360Cameras, setHas360Cameras] = useState(false);
    const [hasParkingSensors, setHasParkingSensors] = useState(false);
    const [photo, setPhoto] = useState(null);

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


    const navigate = useNavigate();
    const token = Cookies.get('authToken') || null;
    if (token === null ) {
        navigate('/login');
        return;
    }

    const options = [
        {'name': 'Apple Car Play', 'state': [hasAppleCarPlay, setHasAppleCarPlay]},
        {'name': 'Head Up Display', 'state': [hasHeadUpDisplay, setHasHeadUpDisplay]},
        {'name': 'Android', 'state': [hasAndroid, setHasAndroid]},
        {'name': 'Bluetooth', 'state': [hasBluetooth, setHasBluetooth]},
        {'name': 'Air Conditioning', 'state': [hasAirConditioning, setHasAirConditioning]},
        {'name': 'Dual Climatic', 'state': [hasDualClimatic, setDualClimatic]},
        {'name': 'Panoramic Roof', 'state': [hasPanoramicRoof, setHasPanoramicRoof]},
        {'name': 'Sun Roof', 'state': [hasSunRoof, setHasSunRoof]},
        {'name': 'Electronic Driver Seat', 'state': [hasElectricDriverSeat, setHasElectricDriverSeat]},
        {'name': 'Electronic Passenger Seat', 'state': [hasElectricPassengerSeat, setHasElectricPassengerSeat]},
        {'name': 'Electric Seats', 'state': [hasElectricSeats, setHasElectricSeats]},
        {'name': 'Heated Driver Seat', 'state': [hasHeatedDriverSeat, setHasHeatedDriverSeat]},
        {'name': 'Heated Passenger Seat', 'state': [hasHeatedPassengerSeat, setHasHeatedPassengerSeat]},
        {'name': 'Heated Steering Wheel', 'state': [hasHeatedSteeringWheel, setHasHeatedSteeringWheel]},
        {'name': 'Heated Windscreen', 'state': [hasHeatedWindscreen, setHasHeatedWindscreen]},
        {'name': 'Cruise Control', 'state': [hasCruiseControl, setHasCruiseControl]},
        {'name': 'Distance Control', 'state': [hasDistanceControl, setHasDistanceControl]},
        {'name': '360 Cameras', 'state': [has360Cameras, setHas360Cameras]},
        {'name': 'Parking Sensors', 'state': [hasParkingSensors, setHasParkingSensors]}
    ]

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

    const handleKmChange = (event) => {
        setKm(event.target.value);
    }

    const handleFuelTypeChange = (event) => {
        setFuelType(event.target.value);
    }

    const handleBodyTypeChange = (event) => {
        setBodyType(event.target.value);
    }

    const handleColorTypeChange = (event) => {
        setColorType(event.target.value);
    }

    const handleNrOfSeatsChange = (event) => {
        setNrOfSeats(event.target.value);
    }

    const handleEmissionLevelTypeChange = (event) => {
        setEmissionLevelType(event.target.value);
    }

    const handleHorsePowerChange = (event) => {
        if (event.target.value < 0 || event.target.value > 2000) {
            toast.error('Invalid horse power value.');
        } else
            setHorsePower(event.target.value);
    }

    const handleCylinderCapacityChange = (event) => {
        setCylinderCapacity(event.target.value);
    }

    const handleTransmissionTypeChange = (event) => {
        setTransmissionType(event.target.value);
    }

    const handleDrivetrainTypeChange = (event) => {
        setDrivetrainType(event.target.value);
    }

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
    
            setPhoto(base64Img);
        }
    
        reader.readAsDataURL(file);
    }

    function handleSubmitButton() {
        if (selectedBrand === '' || selectedModel === '' || selectedGeneration === '' 
            || year === '' || price === '' || fuelType === '') {
            toast.error('Brand, Model, Generation, Price, Year and Fuel Type must be specified.');
            return;
        }

        if (photo === null) {
            toast.error('You have to upload a photo of the vehicle.');
            return;
        }
        
        const data = {
            'brand': selectedBrand,
            'modelName': selectedModel,
            'generationName': selectedGeneration,
            'price': price,
            'year': year,
            'km': km === '' ? null : km,
            'fuelType': fuelType,
            'body': {
                'bodyType': bodyType === '' ? null : bodyType,
                'colorType': colorType === '' ? null : colorType,
                'nrOfSeats': nrOfSeats === '' ? null : nrOfSeats,
            },
            'engine': {
                'emissionLevelType': emissionLevelType === '' ? null : emissionLevelType,
                'horsePower': horsePower === '' ? null : horsePower,
                'cylinderCapacity': cylinderCapacity === '' ? null : cylinderCapacity,
                'transmissionType': transmissionType === '' ? null : transmissionType,
                'drivetrain': drivetrainType === '' ? null : drivetrainType
            },
            'options': {
                'hasAppleCarPlay': hasAppleCarPlay,
                'hasHeadUpDisplay': hasHeadUpDisplay,
                'hasAndroid': hasAndroid,
                'hasBluetooth': hasBluetooth,
                'hasAirConditioning': hasAirConditioning,
                'hasDualClimatic': hasDualClimatic,
                'hasPanoramicRoof': hasPanoramicRoof,
                'hasSunRoof': hasSunRoof,
                'hasElectricDriverSeat': hasElectricDriverSeat,
                'hasElectricPassengerSeat': hasElectricPassengerSeat,
                'hasElectricSeats': hasElectricSeats,
                'hasHeatedDriverSeat': hasHeatedDriverSeat,
                'hasHeatedPassengerSeat': hasHeatedPassengerSeat,
                'hasHeatedSteeringWheel': hasHeatedSteeringWheel,
                'hasHeatedWindscreen': hasHeatedWindscreen,
                'hasCruiseControl': hasCruiseControl,
                'hasDistanceControl': hasDistanceControl,
                'has360Cameras': has360Cameras,
                'hasParkingSensors': hasParkingSensors
            },
            'photos': [photo]
        }
        
        axios.post('http://localhost:8080/vehicles/sell', data, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
            })
            .then(response => {
                navigate('/');
                setTimeout(() => {
                    const myProfileContainer = document.getElementById('my-profile-container');
                    if (myProfileContainer) {
                        const elementTop = myProfileContainer.getBoundingClientRect().top;
                        window.scrollBy({
                            top: elementTop - 80,
                            behavior: 'smooth'
                        })
                    }
                }, 100); 

                toast.success(response.data.message);
            })
            .catch(error => {
                toast.error('An error has occured when trying to add the advert. Please try to re-log.');
                switch(error.response.data.status) {
                    case 400:
                        toast.error(error.response.data.message);
                        break;
                    case 401:
                        toast.error(error.response.data.message);
                        break;
                    default:
                        setTimeout(() => {
                            Cookies.remove('authToken');
                            window.location.reload();
                        }, 2000)
                        break;
                }
                return;
            });

        console.log(data);
    }

    function handleCheckboxChange(option) {
        option.state[1](!option.state[0]);
    }

    return (
        <div id='sell-page-id' className='sell-page'>
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

                    <div className='km-selector'>
                        <label className='km-label'>Kilometers</label>
                        <input id="sell-km-input" type="number" className='sell-numeric-input' value={km} onChange={handleKmChange}/>
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
                    <div className='body-types-selector'>
                        <label className='body-types-label'>Body</label>
                        <select id="sell-body-types-select" className='sell-page-select' value={bodyType} onChange={handleBodyTypeChange}>
                            <option key='' className='sell-page-option' value=''>Select a body type...</option>
                            {bodyTypes.map((bodyType) => {
                                return (
                                    <option key={bodyType} className='sell-page-option' value={bodyType}>{bodyType}</option>
                            )})}
                        </select>
                    </div>

                    <div className='color-types-selector'>
                        <label className='color-types-label'>Color</label>
                        <select id="sell-color-types-select" className='sell-page-select' value={colorType} onChange={handleColorTypeChange}>
                            <option key='' className='sell-page-option' value=''>Select a color type...</option>
                            {colorTypes.map((colorType) => {
                                return (
                                    <option key={colorType} className='sell-page-option' value={colorType}>{colorType}</option>
                            )})}
                        </select>
                    </div>

                    <div className='nr-of-seats-selector'>
                        <label className='nr-of-seats-label'>Nr. of Seats</label>
                        <select id="sell-nr-of-seats-select" className='sell-page-select' value={nrOfSeats} onChange={handleNrOfSeatsChange}>
                            <option key='' className='sell-page-option' value=''>Select nr. of seats...</option>
                            {nrSeats.map((seat) => {
                                return (
                                    <option key={seat} className='sell-page-option' value={seat}>{seat}</option>
                            )})}
                        </select>
                    </div>
                </div>
                <div className='sell-engine-container'>
                    <div className='sell-container-title'>Engine</div>
                    <div className='emission-level-selector'>
                        <label className='emission-level-label'>Emission Level</label>
                        <select id="sell-emission-level-select" className='sell-page-select' value={emissionLevelType} onChange={handleEmissionLevelTypeChange}>
                            <option key='' className='sell-page-option' value=''>Select emission level...</option>
                            {emissionLevelTypes.map((emissionLevel) => {
                                return (
                                    <option key={emissionLevel} className='sell-page-option' value={emissionLevel}>{emissionLevel}</option>
                            )})}
                        </select>
                    </div>
                    
                    <div className='horse-power-selector'>
                        <label className='horse-power-label'>Horse Power</label>
                        <input id="sell-horse-power-input" type="number" className='sell-numeric-input' value={horsePower} onChange={handleHorsePowerChange}/>
                    </div>

                    <div className='cylinder-capacity-selector'>
                        <label className='cylinder-capacity-label'>Cylinder Capacity</label>
                        <select id="sell-cylinder-capacity-select" className='sell-page-select' value={cylinderCapacity} onChange={handleCylinderCapacityChange}>
                            <option key='' className='sell-page-option' value=''>Select cylinder capacity...</option>
                            {cylinderCapacities.map((cylinderCapacity) => {
                                return (
                                    <option key={cylinderCapacity} className='sell-page-option' value={cylinderCapacity}>{cylinderCapacity}</option>
                            )})}
                        </select>
                    </div>

                    <div className='transmission-selector'>
                        <label className='transmission-label'>Transmission</label>
                        <select id="sell-transmission-select" className='sell-page-select' value={transmissionType} onChange={handleTransmissionTypeChange}>
                            <option key='' className='sell-page-option' value=''>Select transmission...</option>
                            {transmissionTypes.map((transmissionType) => {
                                return (
                                    <option key={transmissionType} className='sell-page-option' value={transmissionType}>{transmissionType}</option>
                            )})}
                        </select>
                    </div>

                    <div className='drivetrain-selector'>
                        <label className='drivetrain-label'>Drivetrain</label>
                        <select id="sell-cylinder-capacity-select" className='sell-page-select' value={drivetrainType} onChange={handleDrivetrainTypeChange}>
                            <option key='' className='sell-page-option' value=''>Select drivetrain...</option>
                            {drivetrainTypes.map((drivetrainType) => {
                                return (
                                    <option key={drivetrainType} className='sell-page-option' value={drivetrainType}>{drivetrainType}</option>
                            )})}
                        </select>
                    </div>
                    
                </div>
                <div className='sell-options-container'>
                    <div className='sell-container-title'>Extra-Options</div>

                    <div className='extra-options-container'>
                        {options.map((option) => (<div key={option.name} className='options-checkbox-selector'>
                            <label className='label-styling'>
                                <input type="checkbox" className='checkbox-styling' value={option.state[0]} onChange={() => handleCheckboxChange(option)}/>
                                {option.name}
                            </label>
                        </div>))}
                    </div>

                </div>
                <div className='upload-photo-button-container'>
                    <label htmlFor="photoupload" className="upload-photo-button">
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
    );
}

export default SellVehiclePage;