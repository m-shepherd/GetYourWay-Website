import './App.css';

import LoginAndSignUp from '../LoginAndSignUp/LoginAndSignUp';
import Flights from '../Flights/Flights';

const App = () => {
    return (
        <div className="container">
          {/*<Login />*/}
            <LoginAndSignUp/>
        </div>
    );
}

export default App;