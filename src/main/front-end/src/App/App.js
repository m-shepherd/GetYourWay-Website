import './App.css';
import React from 'react';
import {BrowserRouter as Router, Routes, Route, Link} from "react-router-dom";

import LoginAndSignUp from '../LoginAndSignUp/LoginAndSignUp';
import ResetPassword from "../ResetPassword/ResetPassword";
import MainPage from "../MainPage/MainPage";
import Flights from '../Flights/Flights';

const App = () => {
    return (
        <Router>
            <div>
            <Routes>
                <Route path = "/" element={<LoginAndSignUp/>}/>
                <Route path="/ResetPassword" element={<ResetPassword/>}/>
                <Route path="/MainPage" element={<MainPage/>}/>
                <Route path="/Flights" element={<Flights/>}/>
            </Routes>
          </div>
        </Router>
    );
}

export default App;