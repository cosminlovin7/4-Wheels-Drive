import './HomePage.css';
import Header from './components/Header';
import SearchCar from './components/car-search/SearchCar';
import Footer from './components/Footer';
import MyProfile from './components/my-profile/MyProfile';
import React, { useEffect } from 'react';
import * as fetchData from '../FetchData.js';
import { ToastContainer, toast } from 'react-toastify';

function HomePage(props) {
  useEffect(() => {
    document.title = "4 Wheels Drive";
  }, []);

  let brands = fetchData.getAllBrands();
  let models = fetchData.getAllModels();
  let generations = fetchData.getAllGenerations();
  
  console.log(brands);
  console.log(models);
  console.log(generations);

  return (
    <div className="main">
      <ToastContainer/>
      <Header />  
      <MyProfile
                 vehicleToEdit={props.vehicleToEdit} 
                 setVehicleToEdit={props.setVehicleToEdit}/>
      <SearchCar brands={brands} 
                 models={models} 
                 generations={generations}/>
      <Footer/>
    </div>
  );
}

export default HomePage;
