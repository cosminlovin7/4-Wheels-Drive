import './App.css';
import Header from './components/Header';
import SearchCar from './components/car-search/SearchCar';
import Footer from './components/Footer';
import React, { useEffect } from 'react';
import * as fetchData from './FetchData.js';

function App() {
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
      <Header/>  
      <SearchCar brands={brands} 
                 models={models} 
                 generations={generations}/>
      <Footer/>
    </div>
  );
}

export default App;
