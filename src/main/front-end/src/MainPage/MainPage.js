import {useNavigate} from "react-router-dom";
import mainStyles from'./MainPage.module.css';
import './MainPage.css';
import Weather from '../Weather/Weather'
import Flights from '../Flights/Flights'

const MainPage = () => {

    let navigate = useNavigate();

    console.log(localStorage.getItem('auth'));

    function logOut() {
        localStorage.removeItem('auth');
        navigate('/')
    }

    return (
        <div>
            <div className={mainStyles.padding}>
                <div className={mainStyles.wrapper}>
                    <div className={mainStyles.title_text}>
                        <div className={mainStyles.title}>Main Page</div>
                    </div>
                    <div className={mainStyles.pass_link} onClick={logOut}><a href="">Log Out</a></div>
                    <div className={mainStyles.pass_link}><a href="#findFlights">Find Flights</a></div>
                </div>
            </div>

            <Weather/>
            <Flights/>
            
        </div>
    )
}

export default MainPage;