import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HomePage from './home-page/HomePage';
import LoginRegisterPage from './login-page/LoginRegisterPage';
import { useState } from 'react';

function App() {
  const [authToken, setAuthToken] = useState(null);
  const [vehicleToEdit, setVehicleToEdit] = useState(null);
  
  if (authToken != null) {
    console.log('Authorization Token: ' + authToken['token']);
  }

  return (
    <Router>
      <Routes>
        <Route path="/" element={<HomePage authToken={authToken} setAuthToken={setAuthToken}/>} />
        <Route path="/login" element={<LoginRegisterPage setAuthToken={setAuthToken}/>} />
      </Routes>
    </Router>
  );
}

export default App;

