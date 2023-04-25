import './SellVehiclePage.css'
import { useState, useEffect } from 'react';
import Cookies from 'js-cookie';
import * as fetchData from '../FetchData.js';
import Footer from '../home-page/components/Footer.js';
import NavigationBar from '../home-page/components/NavigationBar.js';

function SellVehiclePage() {
    const token = Cookies.get('authToken') || null;

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
        <div className='sell-page'>
            <NavigationBar/>
            <div className='sell-page-container'>
                <div>Add details about the car you want to sell</div>
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