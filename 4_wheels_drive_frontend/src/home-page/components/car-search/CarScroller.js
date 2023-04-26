import '../../styles/CarScroller.css'
import { MdChevronLeft, MdChevronRight } from 'react-icons/md';
import { useEffect, useState } from 'react';
import { ToastContainer, toast } from 'react-toastify';
import Cookies from 'js-cookie';
import axios from 'axios';

export default function CarScroller(props) {
    const [vehicles, setVehicles] = useState([]);
    const token = Cookies.get('authToken') || null;

    const slideLeft = () => {
        var slider = document.getElementById("scroller-container")
        slider.scrollLeft = slider.scrollLeft - 500;
    }

    const slideRight = () => {
        var slider = document.getElementById("scroller-container");
        slider.scrollLeft = slider.scrollLeft + 500;
    }

    useEffect(() => {
        if (props.filters == null) {
            axios.get(process.env.REACT_APP_GET_ALL_VEHICLES_URL)
                .then(response => {
                    setVehicles(response.data.carAdverts);
                })
                .catch(error => {
                    console.log(error);
                });
        } else {
            var url = process.env.REACT_APP_GET_ALL_VEHICLES_URL + '/filter';
            var firstFilterSet = false;

            if (props.filters['brandSelected']['name'] !== null) {
                url = url + '?brandId=' + props.filters['brandSelected']['id'];
                firstFilterSet = true;
            }

            if (props.filters['modelSelected']['name'] !== null) {
                if (!firstFilterSet) {
                    url = url + '?';
                    firstFilterSet = true;
                } else 
                    url = url + '&';
                
                url = url + 'modelId=' + props.filters['modelSelected']['id'];
            }

            if (props.filters['generationSelected']['name'] !== null) {
                if (!firstFilterSet) {
                    url = url + '?';
                    firstFilterSet = true;
                } else 
                    url = url + '&';
                
                url = url + 'generationId=' + props.filters['generationSelected']['id'];
            }

            if (props.filters['minPrice'] !== '') {
                if (!firstFilterSet) {
                    url = url + '?';
                    firstFilterSet = true;
                } else 
                    url = url + '&';
                
                url = url + 'minPrice=' + props.filters['minPrice'];
            }

            if (props.filters['maxPrice'] !== '') {
                if (!firstFilterSet) {
                    url = url + '?';
                    firstFilterSet = true;
                } else 
                    url = url + '&';
                
                url = url + 'maxPrice=' + props.filters['maxPrice'];
            }

            if (props.filters['minYear'] !== '') {
                if (!firstFilterSet) {
                    url = url + '?';
                    firstFilterSet = true;
                } else 
                    url = url + '&';
                
                url = url + 'minYear=' + props.filters['minYear'];
            }

            if (props.filters['maxYear'] !== '') {
                if (!firstFilterSet) {
                    url = url + '?';
                    firstFilterSet = true;
                } else 
                    url = url + '&';
                
                url = url + 'maxYear=' + props.filters['maxYear'];
            }

            axios.get(url)
                .then(response => {
                    setVehicles(response.data.carAdverts);
                })
                .catch(error => {
                    console.log(error);
                });
        }
    }, [props.applyFilters]);
    

    if (vehicles.length === 0) {
        return (
        <div id="not-found-container">
            <img id="not-found-img" src="https://cdn-icons-png.flaticon.com/512/75/75823.png"/>
        </div>);
    }

    function handleVehicleSelected(vehicle) {
        console.log(vehicle);
        props.setVehicle(vehicle);

        setTimeout(() => {
            const searchCarContainer = document.getElementById('car-info-container');
            if (searchCarContainer) {
                console.log('intra aici.');
                const elementTop = searchCarContainer.getBoundingClientRect().top;
                window.scrollBy({
                    top: elementTop - 80,
                    behavior: 'smooth'
                })
            }
        }, 100);  
    }

    function handleAdDoubleClick(vehicle) {
        if (token !== null) {
            const body = {
                'carAdvertisementId': vehicle['id']
            }
            axios.post('http://localhost:8080/vehicles/add/favorite', body, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
                })
                .then(response => {
                    toast.success('The advert has been added to favorite list.');
                })
                .catch(error => {
                    console.log('error block');
                    switch(error.response.data.status) {
                        case(409):
                            toast.error(error.response.data.message);
                            break;
                        default:
                            toast.error('An error has occured when trying to add the advert to favorite list. Please try to re-log.');
                            setTimeout(() => {
                                Cookies.remove('authToken');
                                window.location.reload();
                            }, 2000);
                            break;
                    }
                });
        } else {
            toast.error('You have to be logged in to add an advert to favorite list.');
        }
    }

    return (
        <div className="car-list-container">
            <MdChevronLeft className="sliders" size={40} onClick={slideLeft}/>
            <div id="scroller-container">
                {
                    vehicles.map((vehicle) => (
                        <div key={vehicle['id']} className="ad-box" onDoubleClick={() => handleAdDoubleClick(vehicle)}>
                            <div className="ad-image">
                                {vehicle['photos'] !== null && (
                                    <img className="image" src={vehicle['photos'][0]} alt='Image'/>
                                )}
                            </div>
                            <div className="car-info">
                                <div className="car-name">
                                    <div id="brand-container">{vehicle['brand']}</div>
                                    <div id="model-generation-container">{vehicle['model']} {vehicle['generation']}</div>
                                </div> 
                                <div className="info-container">
                                    <div className="info-title">
                                        <span className="title-text">Price: </span>
                                    </div>
                                    <span id="price">{vehicle['price'] !== null ? vehicle['price'] : 'n/a'}$</span>
                                </div>
                                <div className="info-container">
                                    <div className="info-title">
                                        <span className="title-text">KM: </span>
                                    </div>
                                    <span id="kilometrage">{vehicle['km'] !== null ? vehicle['km'] : 'n/a'} KM</span>
                                </div>
                                <div className="info-container">
                                    <div className="info-title">
                                        <span className="title-text">Year: </span>
                                    </div> 
                                    <span id="year">{vehicle['year'] !== null ? vehicle['year'] : 'n/a'}</span>
                                </div>
                                <div className="info-container">
                                    <div className="info-title">
                                        <span className="title-text">Country: </span>
                                    </div>
                                    <span id="country">{vehicle['country'] !== null ? vehicle['country'] : 'n/a'}</span>
                                </div>
                                <div className="info-container">
                                    <div className="info-title">
                                        <span className="title-text">Fuel Type: </span>
                                    </div>
                                    <span id="fuelType">{vehicle['fuelType'] !== null ? vehicle['fuelType'] : 'n/a'}</span>
                                </div>
                            </div>
                            <div className="view-details-container" onClick={() => handleVehicleSelected(vehicle)}>
                                    <div className="view-details-button">
                                        Details
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