import './App.css';
import Header from './components/Header';
import SearchCar from './components/car-search/SearchCar';
import Footer from './components/Footer';
import React, { useEffect } from 'react';

function App() {
  useEffect(() => {
    document.title = "4 Wheels Drive";
  }, []);

  return (
    <div className="main">
      <Header/>  
      <SearchCar/>
      <Footer/>
    </div>
  );
}

export default App;
