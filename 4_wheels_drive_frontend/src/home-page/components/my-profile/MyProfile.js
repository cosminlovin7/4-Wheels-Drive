import './MyProfile.css'
import { useEffect, useState } from 'react';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import ViewCarInfo from '../car-search/ViewCarInfo.js';
import Cookies from 'js-cookie';
import { useNavigate } from 'react-router-dom';

function MyProfile(props) {
    const navigate = useNavigate();
    const [advertisementsTabActive, setAdvertisementsTabActive] = useState(true);
    const [favoritesTabActive, setFavoritesTabActive] = useState(false);
    const [userAdverts, setUserAdverts] = useState([]);
    const [userFavorites, setUserFavorites] = useState([]);
    const [vehicleToDisplay, setVehicleToDisplay] = useState(null);
    const [currentUserAdvertPage, setCurrentUserAdvertPage] = useState(1);
    const [currentUserFavoritePage, setCurrentUserFavoritePage] = useState(1);

    const totalUserAdvertPages = Math.ceil(userAdverts.length / 4);
    const startIndexA = (currentUserAdvertPage - 1) * 4;
    const endIndexA = startIndexA + 4;

    const userAdvertsToDisplay = userAdverts.slice(startIndexA, endIndexA);

    const totalUserFavoritePages = Math.ceil(userFavorites.length / 4);
    const startIndexF = (currentUserFavoritePage - 1) * 4;
    const endIndexF = startIndexF + 4;

    const userFavoritesToDisplay = userFavorites.slice(startIndexF, endIndexF);

    const token = Cookies.get('authToken') || null;

    useEffect(() => {
        if (token !== null) {
            axios.get('http://localhost:8080/vehicles/favorites', {
                headers: {
                    'Authorization': `Bearer ${token}`,
                },
                })
                .then(response => {
                    setUserFavorites(response.data.carAdverts);
                })
                .catch(error => {
                    Cookies.remove('authToken');
                    window.location.reload();
                    toast.error('An error has occured.');
                });
        }
    }, [favoritesTabActive]);

    useEffect(() => {
        if (token !== null) {
            axios.get('http://localhost:8080/vehicles/adverts', {
                headers: {
                    'Authorization': `Bearer ${token}`,
                },
                })
                .then(response => {
                    setUserAdverts(response.data.carAdverts);
                })
                .catch(error => {
                    Cookies.remove('authToken');
                    window.location.reload();
                    toast.error('An error has occured.');
                });
        }
    }, [advertisementsTabActive]);

    function handleTabButton(buttonName) {
        const adsContainer = document.getElementById('my-advertisements-container');
        const favoritesContainer = document.getElementById('favorites-container');
        const hideShowButton = document.getElementById('my-hide-button');

        if ((buttonName === 'my-advertisements' && favoritesTabActive === true) || 
            (buttonName === 'favorites' && advertisementsTabActive === true)) {
            setAdvertisementsTabActive(!advertisementsTabActive);
            setFavoritesTabActive(!favoritesTabActive);
            setVehicleToDisplay(null);
        }

        if (buttonName === 'favorites') {
            adsContainer.style.display = 'none';
            if (hideShowButton.textContent === 'Hide')
                favoritesContainer.style.display = 'block';
        
        }

        if (buttonName === 'my-advertisements') {
            favoritesContainer.style.display = 'none';
            if (hideShowButton.textContent === 'Hide')
                adsContainer.style.display = 'block';

        }
    }

    function handleAdvertContainerClick(car) {
        setVehicleToDisplay(car);
        setTimeout(() => {
            const myCarInfo = document.getElementById('my-car-info');
            if (myCarInfo) {
                const elementTop = myCarInfo.getBoundingClientRect().top;
                window.scrollBy({
                    top: elementTop - 80,
                    behavior: 'smooth'
                })
            }
        }, 100);  
    }

    function handleHideShowButton() {
        const hideShowButton = document.getElementById('my-hide-button');
        const myAdContainer = document.getElementById('my-advertisements-container');
        const myFavContainer = document.getElementById('favorites-container');
        const myCarInfo = document.getElementById('my-car-info');

        if (hideShowButton.textContent === 'Hide') {
            hideShowButton.textContent = 'Show';
            if (favoritesTabActive === true)
                myFavContainer.style.display = 'none';
            else 
                myAdContainer.style.display = 'none';

            if (myCarInfo !== null) 
                myCarInfo.style.display = 'none';
        } else {
            hideShowButton.textContent = 'Hide';
            if (favoritesTabActive === true)
                myFavContainer.style.display = 'block';
            else
                myAdContainer.style.display = 'block';
                
            if (myCarInfo !== null)
                myCarInfo.style.display = 'block';
        }
    }

    function handleDeleteUserAdvert(userAdvert) {
        axios.delete('http://localhost:8080/vehicles/delete?car_advert_id=' + userAdvert['id'], {
            headers: {
                'Authorization': `Bearer ${token}`
            }
            })
            .then(response => {
                const updatedUserAdverts = userAdverts.filter((advert) => advert['id'] !== userAdvert['id']);
                const myCarInfo = document.getElementById('my-car-info');
                setUserAdverts(updatedUserAdverts);
                
                setVehicleToDisplay(null);
                if (myCarInfo !== null) 
                    myCarInfo.style.display = 'none';

                toast.success('The advert has been deleted.');
            })
            .catch(error => {
                console.log('error block');
                toast.error('An error has occured when trying to delete the advert. Please try to re-log.');
                setTimeout(() => {
                    Cookies.remove('authToken');
                    window.location.reload();
                }, 2000)
                return;
            });
    }

    function handleEditUserAdvert(userAdvert) {
        props.setVehicleToEdit(userAdvert);
        navigate('/vehicles/edit');
        setTimeout(() => {
            const editPageContainer = document.getElementById('edit-page-id');
            if (editPageContainer) {
                const elementTop = editPageContainer.getBoundingClientRect().top;
                window.scrollBy({
                    top: elementTop,
                    behavior: 'smooth'
                })
            }
        }, 100); 
        
    }

    function handleRemoveFavoriteAdvert(userFavorite) {
        const body = {
            'carAdvertisementId': userFavorite['id']
        }
        axios.post('http://localhost:8080/vehicles/delete/favorite', body, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
            })
            .then(response => {
                const updatedUserFavorites = userFavorites.filter((advert) => advert['id'] !== userFavorite['id']);
                const myCarInfo = document.getElementById('my-car-info');
                setUserFavorites(updatedUserFavorites);
                
                setVehicleToDisplay(null);
                if (myCarInfo !== null) 
                    myCarInfo.style.display = 'none';

                toast.success('The advert has been removed from favorite list.');
            })
            .catch(error => {
                console.log('error block');
                toast.error('An error has occured when trying to remove the advert. Please try to re-log.');
                setTimeout(() => {
                    Cookies.remove('authToken');
                    window.location.reload();
                }, 2000)
                return;
            });
    }

    function handleUserAdvertPageChange(pageNumber) {
        setCurrentUserAdvertPage(pageNumber);
    }

    function handleUserFavoritePageChange(pageNumber) {
        setCurrentUserFavoritePage(pageNumber);
    }

    return (
        <>
            {token !== null && (
                <div id="my-profile">
                    <div id="my-profile-container">
                        <div className="my-profile-title-container">
                            <div className="my-profile-title">
                                My Profile
                            </div>
                        </div>
                        <div className="tab-container">
                            <div className="ad-fav-container">
                                <div className="tab"
                                    onClick={() => {handleTabButton('my-advertisements')}}>
                                    <div className={advertisementsTabActive === true ? "tab-title-container" : "tab-title-container tab-title-container-not-selected"}>
                                        My Advertisements
                                    </div>
                                </div>
                                <div className="tab"
                                    onClick={() => {handleTabButton('favorites')}}>
                                    <div className={favoritesTabActive === true ? "tab-title-container" : "tab-title-container tab-title-container-not-selected"}>
                                        Favorites
                                    </div>
                                </div>
                            </div>
                            <div className="hide-button-container" onClick={handleHideShowButton}>
                                <div id="my-hide-button">Hide</div>
                            </div>
                        </div>
                        <div id="my-advertisements-container">
                            {
                                userAdvertsToDisplay.map((userAdvert) => (
                                    <div key={userAdvert['id']} className="my-adverts-container">
                                        <div className="edit-delete-container">
                                            <div className="edit-button" onClick={() => {handleEditUserAdvert(userAdvert)}}>
                                                <img src="https://cdn-icons-png.flaticon.com/512/3597/3597088.png" className="edit-image" alt="Delete Button"/>
                                            </div>
                                            <div className="delete-button" onClick={() => {handleDeleteUserAdvert(userAdvert)}}>
                                                <img src="https://cdn-icons-png.flaticon.com/512/1345/1345874.png" className="delete-image"/>
                                            </div>
                                        </div>
                                        <div className="my-advert-wrapper" onClick={() => {handleAdvertContainerClick(userAdvert)}}>
                                            <div className="my-image-container">
                                                <img className="my-img" src={userAdvert['photos'][0]}/>
                                            </div>
                                            <div className="my-info-container">
                                                <div className="my-brand-container">{userAdvert['brand']} {userAdvert['model']} {userAdvert['generation']}</div>
                                            </div>
                                        </div>
                                    </div>)
                                )
                            }
                            <div className="page-buttons-container">
                                <div>
                                    {Array.from({ length: totalUserAdvertPages }).map((_, i) => (
                                    <button
                                        key={i}
                                        onClick={() => handleUserAdvertPageChange(i + 1)}
                                        disabled={currentUserAdvertPage === i + 1}
                                        className="page-button"
                                    >
                                        {i + 1}
                                    </button>
                                    ))}
                                </div>
                            </div>
                        </div>
                        <div id="favorites-container">
                            {
                                userFavoritesToDisplay.map((userFavorite) => (
                                    <div key={userFavorite['id']} className="my-adverts-container">
                                        <div className="remove-advert-container" onClick={() => {handleRemoveFavoriteAdvert(userFavorite)}}>
                                            <img src="https://cdn-icons-png.flaticon.com/512/75/75519.png" className="remove-image"/>
                                        </div>
                                        <div className="my-advert-wrapper" onClick={() => {handleAdvertContainerClick(userFavorite)}}>
                                            <div className="my-image-container">
                                                <img className="my-img" src={userFavorite['photos'][0]}/>
                                            </div>
                                            <div className="my-info-container">
                                                <div className="my-brand-container">{userFavorite['brand']} {userFavorite['model']} {userFavorite['generation']}</div>
                                            </div>
                                        </div>
                                    </div>)
                                )
                            }
                            <div className="page-buttons-container">
                                <div>
                                    {Array.from({ length: totalUserFavoritePages }).map((_, i) => (
                                    <button
                                        key={i}
                                        onClick={() => handleUserFavoritePageChange(i + 1)}
                                        disabled={currentUserFavoritePage === i + 1}
                                        className="page-button"
                                    >
                                        {i + 1}
                                    </button>
                                    ))}
                                </div>
                            </div>
                        </div>
                    </div>
                    <ViewCarInfo id='my-car-info' vehicle={vehicleToDisplay}/>
                </div>
            )}
        </>
    );
}

export default MyProfile;