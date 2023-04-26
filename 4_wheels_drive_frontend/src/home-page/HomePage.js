import './HomePage.css';
import Header from './components/Header';
import SearchCar from './components/car-search/SearchCar';
import Footer from './components/Footer';
import MyProfile from './components/my-profile/MyProfile';
import React, { useEffect } from 'react';
import * as fetchData from '../FetchData.js';
import { ToastContainer, toast } from 'react-toastify';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

function HomePage(props) {
  const navigate = useNavigate();
  useEffect(() => {
    document.title = "4 Wheels Drive";

    const queryParams = new URLSearchParams(window.location.search);
    const username = queryParams.get("username");
    const token = queryParams.get("token");

    if (username === null || token === null) {
      navigate('/');
      return;
    }

    axios.get('http://localhost:8080/register/confirm?username=' + username + '&token=' + token)
        .then(response => {
          toast.success(response.data.message);
        })
        .catch(error => {
          navigate('/');
        });
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
