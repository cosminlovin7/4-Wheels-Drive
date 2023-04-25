import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HomePage from './home-page/HomePage';
import LoginRegisterPage from './login-page/LoginRegisterPage';
import SellVehiclePage from './sell-page/SellVehiclePage';
import EditVehiclePage from './edit-page/EditVehiclePage';
import NotFoundPage from './not-found-page/NotFoundPage';
import { useState } from 'react';

function App() {
  const [vehicleToEdit, setVehicleToEdit] = useState(null);

  return (
    <Router>
      <Routes>
        <Route path="/" 
               element={<HomePage vehicleToEdit={vehicleToEdit} 
                                  setVehicleToEdit={setVehicleToEdit}/>
                        } />
        <Route path="/login" 
               element={<LoginRegisterPage/>} />
        <Route path="/vehicles/sell" 
               element={<SellVehiclePage vehicleToEdit={vehicleToEdit} 
                                         setVehicleToEdit={setVehicleToEdit}/>} />
        <Route path="/vehicles/edit" 
               element={<EditVehiclePage vehicleToEdit={vehicleToEdit} 
                                         setVehicleToEdit={setVehicleToEdit}/>} />
        <Route path="*" element={<NotFoundPage/>}/>
      </Routes>
    </Router>
  );
}

export default App;

