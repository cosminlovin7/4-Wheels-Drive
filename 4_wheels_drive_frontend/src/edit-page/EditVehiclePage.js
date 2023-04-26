import './EditVehiclePage.css'
import { useState, useEffect } from 'react';
import Cookies from 'js-cookie';
import axios from 'axios';
import Footer from '../home-page/components/Footer.js';
import NavigationBar from '../home-page/components/NavigationBar.js';
import { ToastContainer, toast } from 'react-toastify';
import { useNavigate } from 'react-router-dom';

function EditVehiclePage(props) {
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

    const navigate = useNavigate();
    const token = Cookies.get('authToken') || null;
    const [selectedBrand, setSelectedBrand] = useState(props.vehicleToEdit !== null ? props.vehicleToEdit.brand : '');
    const [selectedModel, setSelectedModel] = useState(props.vehicleToEdit !== null ? props.vehicleToEdit.model : '');
    const [selectedGeneration, setSelectedGeneration] = useState(props.vehicleToEdit !== null ? props.vehicleToEdit.generation : '');
    const [brands, setBrands] = useState([]);
    const [models, setModels] = useState([]);
    const [generations, setGenerations] = useState([]);
    const [price, setPrice] = useState(props.vehicleToEdit !== null ? props.vehicleToEdit.price : '');
    const [year, setYear] = useState(props.vehicleToEdit !== null ? props.vehicleToEdit.year : '');
    const [country, setCountry] = useState((props.vehicleToEdit !== null && props.vehicleToEdit.country !== null) ? props.vehicleToEdit.country : '');
    const [km, setKm] = useState((props.vehicleToEdit !== null && props.vehicleToEdit.km !== null) ? props.vehicleToEdit.km : '');
    const [fuelType, setFuelType] = useState(props.vehicleToEdit !== null ? props.vehicleToEdit.fuelType : '');
    const [bodyType, setBodyType] = useState((props.vehicleToEdit !== null && props.vehicleToEdit.body.bodyType !== null) ? props.vehicleToEdit.body.bodyType : '');
    const [colorType, setColorType] = useState((props.vehicleToEdit !== null && props.vehicleToEdit.body.colorType !== null) ? props.vehicleToEdit.body.colorType : '');
    const [nrOfSeats, setNrOfSeats] = useState((props.vehicleToEdit !== null && props.vehicleToEdit.body.nrOfSeats !== null) ? props.vehicleToEdit.body.nrOfSeats : '');
    const [emissionLevelType, setEmissionLevelType] = useState((props.vehicleToEdit !== null && props.vehicleToEdit.engine.emissionLevelType !== null) ? props.vehicleToEdit.engine.emissionLevelType : '');
    const [horsePower, setHorsePower] = useState((props.vehicleToEdit !== null && props.vehicleToEdit.engine.horsePower !== null) ? props.vehicleToEdit.engine.horsePower : '');
    const [cylinderCapacity, setCylinderCapacity] = useState((props.vehicleToEdit !== null && props.vehicleToEdit.engine.cylinderCapacity !== null) ? props.vehicleToEdit.engine.cylinderCapacity : '');
    const [transmissionType, setTransmissionType] = useState((props.vehicleToEdit !== null && props.vehicleToEdit.engine.transmissionType !== null) ? props.vehicleToEdit.engine.transmissionType : '');
    const [drivetrainType, setDrivetrainType] = useState((props.vehicleToEdit !== null && props.vehicleToEdit.engine.drivetrainType !== null) ? props.vehicleToEdit.engine.drivetrainType : '');

    const [hasAppleCarPlay, setHasAppleCarPlay] = useState(props.vehicleToEdit !== null ? props.vehicleToEdit.options.hasAppleCarPlay : false);
    const [hasHeadUpDisplay, setHasHeadUpDisplay] = useState(props.vehicleToEdit !== null ? props.vehicleToEdit.options.hasHeadUpDisplay : false);
    const [hasAndroid, setHasAndroid] = useState(props.vehicleToEdit !== null ? props.vehicleToEdit.options.hasAndroid : false);
    const [hasBluetooth, setHasBluetooth] = useState(props.vehicleToEdit !== null ? props.vehicleToEdit.options.hasBluetooth : false);
    const [hasAirConditioning, setHasAirConditioning] = useState(props.vehicleToEdit !== null ? props.vehicleToEdit.options.hasAirConditioning : false);
    const [hasDualClimatic, setDualClimatic] = useState(props.vehicleToEdit !== null ? props.vehicleToEdit.options.hasDualClimatic : false);
    const [hasPanoramicRoof, setHasPanoramicRoof] = useState(props.vehicleToEdit !== null ? props.vehicleToEdit.options.hasPanoramicRoof : false);
    const [hasSunRoof, setHasSunRoof] = useState(props.vehicleToEdit !== null ? props.vehicleToEdit.options.hasSunRoof : false);
    const [hasElectricDriverSeat, setHasElectricDriverSeat] = useState(props.vehicleToEdit !== null ? props.vehicleToEdit.options.hasElectricDriverSeat : false);
    const [hasElectricPassengerSeat, setHasElectricPassengerSeat] = useState(props.vehicleToEdit !== null ? props.vehicleToEdit.options.hasElectricPassengerSeat : false);
    const [hasElectricSeats, setHasElectricSeats] = useState(props.vehicleToEdit !== null ? props.vehicleToEdit.options.hasElectricSeats : false);
    const [hasHeatedDriverSeat, setHasHeatedDriverSeat] = useState(props.vehicleToEdit !== null ? props.vehicleToEdit.options.hasHeatedDriverSeat : false);
    const [hasHeatedPassengerSeat, setHasHeatedPassengerSeat] = useState(props.vehicleToEdit !== null ? props.vehicleToEdit.options.hasHeatedPassengerSeat : false);
    const [hasHeatedSteeringWheel, setHasHeatedSteeringWheel] = useState(props.vehicleToEdit !== null ? props.vehicleToEdit.options.hasHeatedSteeringWheel : false);
    const [hasHeatedWindscreen, setHasHeatedWindscreen] = useState(props.vehicleToEdit !== null ? props.vehicleToEdit.options.hasHeatedWindscreen : false);
    const [hasCruiseControl, setHasCruiseControl] = useState(props.vehicleToEdit !== null ? props.vehicleToEdit.options.hasCruiseControl : false);
    const [hasDistanceControl, setHasDistanceControl] = useState(props.vehicleToEdit !== null ? props.vehicleToEdit.options.hasDistanceControl : false);
    const [has360Cameras, setHas360Cameras] = useState(props.vehicleToEdit !== null ? props.vehicleToEdit.options.has360Cameras : false);
    const [hasParkingSensors, setHasParkingSensors] = useState(props.vehicleToEdit !== null ? props.vehicleToEdit.options.hasParkingSensors : false);
    const [photo, setPhoto] = useState(props.vehicleToEdit !== null ? props.vehicleToEdit.photos[0] : null);

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

    if (token === null) {
        navigate('/login');
        return;
    }

    if (props.vehicleToEdit === null) {
        navigate('/');
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

    const handleCountryChange = (event) => {
        const regex = /^[A-Za-z]+$/;

        if (regex.test(event.target.value) || event.target.value === '') {
            setCountry(event.target.value);
        } else {
            toast.error('You can only use alphabetical characters.')
        }
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
            'country': country === '' ? null : country,
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
        
        axios.put('http://localhost:8080/vehicles/edit?car_advert_id=' + props.vehicleToEdit.id, data, {
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

    function isChecked(state) {
        console.log(state);
        return true;
    }

    function handleCheckboxChange(option) {
        option.state[1](!option.state[0]);
    }

    return (
        <div id='edit-page-id' className='edit-page'>
            <NavigationBar/>
            <ToastContainer/>
            <div className='edit-page-container'>
                <div className='edit-details-container'>
                    <div className='edit-container-title'>Details</div>
                    <div className='edit-brand-selector'>
                        <label className='edit-brand-label'>Brand</label>
                        <select id="edit-brand-select" className='edit-page-select' value={selectedBrand} onChange={handleBrandChange}>
                            <option key='' className='edit-page-option' value=''>Select a brand...</option>
                            {brands.map((brand) => {
                                return (
                                    <option key={brand['id']} className='edit-page-option' value={brand['brandName']}>{brand['brandName']}</option>
                            )})}
                        </select>
                    </div>

                    <div className='edit-model-selector'>
                        <label className='edit-model-label'>Model</label>
                        <select id="edit-model-select" className='edit-page-select' value={selectedModel} onChange={handleModelChange}>
                            <option key='' className='edit-page-option' value=''>Select a model...</option>
                            {brandModels.map((model) => {
                                return (
                                    <option key={model['id']} className='edit-page-option' value={model['modelName']}>{model['modelName']}</option>
                            )})}
                        </select>
                    </div>

                    <div className='edit-generation-selector'>
                        <label className='edit-generation-label'>Generation</label>
                        <select id="edit-generation-select" className='edit-page-select' value={selectedGeneration} onChange={handleGenerationChange}>
                            <option key='' className='edit-page-option' value=''>Select a generation...</option>
                            {modelGenerations.map((generation) => {
                                return (
                                    <option key={generation['id']} className='edit-page-option' value={generation['generationName']}>{generation['generationName']}</option>
                            )})}
                        </select>
                    </div>

                    <div className='edit-price-selector'>
                        <label className='edit-price-label'>Price</label>
                        <input id="edit-price-input" type="number" className='edit-numeric-input' value={price} onChange={handlePriceChange}/>
                    </div>

                    <div className='edit-year-selector'>
                        <label className='edit-year-label'>Year</label>
                        <input id="edit-year-input" type="number" className='edit-numeric-input' value={year} onChange={handleYearChange}/>
                    </div>

                    <div className='edit-km-selector'>
                        <label className='edit-km-label'>Kilometers</label>
                        <input id="edit-km-input" type="number" className='edit-numeric-input' value={km} onChange={handleKmChange}/>
                    </div>

                    <div className='edit-country-selector'>
                        <label className='edit-country-label'>Country</label>
                        <input id="edit-country-input" type="text" className='edit-numeric-input' value={country} onChange={handleCountryChange}/>
                    </div>

                    <div className='edit-fuel-types-selector'>
                        <label className='edit-fuel-types-label'>Fuel Type</label>
                        <select id="edit-fuel-types-select" className='edit-page-select' value={fuelType} onChange={handleFuelTypeChange}>
                            <option key='' className='edit-page-option' value=''>Select a fuel type...</option>
                            {fuelTypes.map((fuelType) => {
                                return (
                                    <option key={fuelType} className='edit-page-option' value={fuelType}>{fuelType}</option>
                            )})}
                        </select>
                    </div>
                </div>
                <div className='edit-body-container'>
                    <div className='edit-container-title'>Body</div>
                    <div className='edit-body-types-selector'>
                        <label className='edit-body-types-label'>Body</label>
                        <select id="edit-body-types-select" className='edit-page-select' value={bodyType} onChange={handleBodyTypeChange}>
                            <option key='' className='edit-page-option' value=''>Select a body type...</option>
                            {bodyTypes.map((bodyType) => {
                                return (
                                    <option key={bodyType} className='edit-page-option' value={bodyType}>{bodyType}</option>
                            )})}
                        </select>
                    </div>

                    <div className='edit-color-types-selector'>
                        <label className='edit-color-types-label'>Color</label>
                        <select id="edit-color-types-select" className='edit-page-select' value={colorType} onChange={handleColorTypeChange}>
                            <option key='' className='edit-page-option' value=''>Select a color type...</option>
                            {colorTypes.map((colorType) => {
                                return (
                                    <option key={colorType} className='edit--page-option' value={colorType}>{colorType}</option>
                            )})}
                        </select>
                    </div>

                    <div className='edit-nr-of-seats-selector'>
                        <label className='edit-nr-of-seats-label'>Nr. of Seats</label>
                        <select id="edit-nr-of-seats-select" className='edit-page-select' value={nrOfSeats} onChange={handleNrOfSeatsChange}>
                            <option key='' className='edit-page-option' value=''>Select nr. of seats...</option>
                            {nrSeats.map((seat) => {
                                return (
                                    <option key={seat} className='edit-page-option' value={seat}>{seat}</option>
                            )})}
                        </select>
                    </div>
                </div>
                <div className='edit-engine-container'>
                    <div className='edit-container-title'>Engine</div>
                    <div className='edit-emission-level-selector'>
                        <label className='edit-emission-level-label'>Emission Level</label>
                        <select id="edit-emission-level-select" className='edit-page-select' value={emissionLevelType} onChange={handleEmissionLevelTypeChange}>
                            <option key='' className='edit-page-option' value=''>Select emission level...</option>
                            {emissionLevelTypes.map((emissionLevel) => {
                                return (
                                    <option key={emissionLevel} className='edit-page-option' value={emissionLevel}>{emissionLevel}</option>
                            )})}
                        </select>
                    </div>
                    
                    <div className='edit-horse-power-selector'>
                        <label className='edit-horse-power-label'>Horse Power</label>
                        <input id="edit-horse-power-input" type="number" className='edit-numeric-input' value={horsePower} onChange={handleHorsePowerChange}/>
                    </div>

                    <div className='edit-cylinder-capacity-selector'>
                        <label className='edit-cylinder-capacity-label'>Cylinder Capacity</label>
                        <select id="edit-cylinder-capacity-select" className='edit-page-select' value={cylinderCapacity} onChange={handleCylinderCapacityChange}>
                            <option key='' className='edit-page-option' value=''>Select cylinder capacity...</option>
                            {cylinderCapacities.map((cylinderCapacity) => {
                                return (
                                    <option key={cylinderCapacity} className='edit-page-option' value={cylinderCapacity}>{cylinderCapacity}</option>
                            )})}
                        </select>
                    </div>

                    <div className='edit-transmission-selector'>
                        <label className='edit-transmission-label'>Transmission</label>
                        <select id="edit-transmission-select" className='edit-page-select' value={transmissionType} onChange={handleTransmissionTypeChange}>
                            <option key='' className='edit-page-option' value=''>Select transmission...</option>
                            {transmissionTypes.map((transmissionType) => {
                                return (
                                    <option key={transmissionType} className='edit-page-option' value={transmissionType}>{transmissionType}</option>
                            )})}
                        </select>
                    </div>

                    <div className='edit-drivetrain-selector'>
                        <label className='edit-drivetrain-label'>Drivetrain</label>
                        <select id="edit-cylinder-capacity-select" className='edit-page-select' value={drivetrainType} onChange={handleDrivetrainTypeChange}>
                            <option key='' className='edit-page-option' value=''>Select drivetrain...</option>
                            {drivetrainTypes.map((drivetrainType) => {
                                return (
                                    <option key={drivetrainType} className='edit-page-option' value={drivetrainType}>{drivetrainType}</option>
                            )})}
                        </select>
                    </div>
                    
                </div>
                <div className='edit-options-container'>
                    <div className='edit-container-title'>Extra-Options</div>

                    <div className='edit-extra-options-container'>
                        {options.map((option) => (<div key={option.name} className='edit-options-checkbox-selector'>
                            <label className='edit-label-styling'>
                                <input type="checkbox" className='edit-checkbox-styling' checked={option.state[0]} onChange={() => handleCheckboxChange(option)}/>
                                {option.name}
                            </label>
                        </div>))}
                    </div>

                </div>

                <div className='edit-image-container'>
                    <div className='edit-image-wrapper'>
                        <img src={photo}/>
                    </div>
                </div>

                <div className='edit-upload-photo-button-container'>
                    <label htmlFor="photoupload" className="edit-upload-photo-button">
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
                <div className='edit-submit-button-container'>
                    <div className='edit-submit-button' onClick={handleSubmitButton}>Submit</div>
                </div>
            </div>
            <Footer/>

        </div>
    );
}


export default EditVehiclePage;