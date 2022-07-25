import {useNavigate} from "react-router-dom";
import mainStyles from'./MainPage.module.css';
import './MainPage.css';
import Flights from '../Flights/Flights'

const MainPage = () => {

    let navigate = useNavigate();

    return (
        <div>
            <div className={mainStyles.padding}>
                <div className={mainStyles.wrapper}>
                    <div className={mainStyles.title_text}>
                        <div className={mainStyles.title}>Main Page</div>
                    </div>
                    <div className={mainStyles.pass_link} onClick={() => navigate('/')}><a href="">Log Out</a></div>
                    <div className={mainStyles.pass_link}><a href="#findFlights">Find Flights</a></div>
                </div>
            </div>

            <Flights/>
            
        </div>
    )
}

export default MainPage;