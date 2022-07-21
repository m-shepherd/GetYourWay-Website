import './App.css';
import React from 'react';
import {BrowserRouter as Router, Routes, Route, Link} from "react-router-dom";

import LoginAndSignUp from '../LoginAndSignUp/LoginAndSignUp';
import ResetPassword from "../ResetPassword/ResetPassword";


const App = () => {
    return (
        <Router>
            <div>
            <nav>
                <ul>
                    <li>
                        <Link to="/">Login and Sign-up</Link>
                    </li>
                    <li>
                        <Link to="/ResetPassword">Reset Password</Link>
                    </li>
                </ul>
            </nav>
            <Routes>
                <Route path = "/" element={<LoginAndSignUp/>}/>
                <Route path="/ResetPassword" element={<ResetPassword/>}/>
            </Routes>
          </div>
        </Router>
    );
}

export default App;