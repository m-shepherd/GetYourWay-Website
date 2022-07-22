import {useNavigate} from "react-router-dom";
import mainStyles from'./MainPage.module.css';
import './MainPage.css';
import flightStyles from "../Flights/Flights.module.css";
import '../Flights/Flights.css';
import {getFlights} from '../Flights/Flights';

const MainPage = () => {

    let navigate = useNavigate();

    return (
        <div>
            <div className={flightStyles.padding}>
                <div className={mainStyles.wrapper}>
                    <div className={mainStyles.title_text}>
                        <div className={mainStyles.title}>Main Page</div>
                    </div>
                    <div className={mainStyles.pass_link} onClick={() => navigate('/')}><a href="">Log Out</a></div>
                    <div className={mainStyles.pass_link}><a href="#findFlights">Find Flights</a></div>
                </div>
            </div>

            <div className={flightStyles.padding}>
                <div className={flightStyles.wrapper}>
                    <div className={flightStyles.form_container}>
                        <div className={flightStyles.form_inner}>
                            <form id="findFlights" onSubmit={getFlights}>
                                <div className={flightStyles.field}>
                                    <input type="date" name="date" min={new Date().toISOString().split('T')[0]} placeholder="YYYY-MM-DD"/>
                                </div>
                                <div className={flightStyles.field}>
                                    <input type="text" name="departureAirport" placeholder="Departure Airport"/>
                                </div>
                                <div className={flightStyles.field}>
                                    <input type="text" name="arrivalAirport" placeholder="Arrival Airport"/>
                                </div>
                                <div className={`${flightStyles.field} ${flightStyles.btn}`}>
                                    <div className={flightStyles.btn_layer}></div>
                                    <input type="submit" value="Find Flights"/>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div id="dataTitle" className={flightStyles.title_text} style={{display: "none"}}>
                        <div className={flightStyles.title}>Flight Data</div>
                    </div>
                    <div className={flightStyles.padding}>
                        <div id="flightData" className={flightStyles.tableFixHead} style={{display: "none"}}>
                            <table id="table">
                                <thead>
                                {/*<tr id="header">*/}
                                <th>Departure Airport</th>
                                <th>Departure Time</th>
                                <th>Arrival Airport</th>
                                <th>Arrival Time</th>
                                <th>Airline</th>
                                <th>Flight Number</th>
                                {/*</tr>*/}
                                </thead>
                                <tbody id="flightTable"></tbody>
                            </table>
                        </div>
                    </div>
                    <div id="destination" className={`${flightStyles.field} ${flightStyles.btn}`} style={{display: "none"}}>
                        <div className={flightStyles.btn_layer}></div>
                        <input id="confirm" type="submit" value="Confirm Flight"/>
                    </div>
                </div>
            </div>

            
        </div>
    )
}

export default MainPage;