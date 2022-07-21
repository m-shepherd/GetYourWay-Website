import './Flights.css';

const Flights = () => {

    function getFlights(event) {
        const xhr = new XMLHttpRequest();
        xhr.open("GET", "http://localhost:8080/flights/2022-07-13&LHR&FRA", true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4)  {
                const flights = JSON.parse(xhr.responseText);
                const flightTable = document.querySelector("#flightTable");
                const flightData = document.querySelector("#flightData");
                const dataTitle = document.querySelector("#dataTitle");
                flightData.style.display = "block";
                dataTitle.style.display = "block";
                let i = 0
                for(const flight in flights) {
                    i++;
                    // if (i <= 3) {
                        const row = flightTable.insertRow(-1)
                        let j = 0;
                        for (const key in flights[flight]) {
                            if (j !== 0) {
                                const cell = row.insertCell(j - 1);
                                cell.innerHTML = flights[flight][key];
                            }
                            j ++;
                        }
                    // }
                }
            }
        };
        xhr.send();
    }

    return (
        <div>
            <div className="wrapper">
                <div className="form-container">
                    <div className="form-inner">
                        <form onSubmit={getFlights}>
                            <div className="field">
                                <input type="date" name="date" placeholder="YYYY-MM-DD"/>
                            </div>
                            <div className="field">
                                <input type="text" name="departureAirport" placeholder="Departure Airport"/>
                            </div>
                            <div className="field">
                                <input type="text" name="arrivalAirport" placeholder="Arrival Airport"/>
                            </div>
                            <div className="field btn">
                                <div className="btn-layer"></div>
                                <input type="submit" value="Find Flights"/>
                            </div>
                        </form>
                    </div>
                </div>
                <div id="dataTitle" className="title-text" style={{display: "none"}}>
                    <div className="title login">Flight Data</div>
                </div>
                <div id="flightData" className="tableFixHead" style={{display: "none"}}>
                    <table id="flightTable">
                        <thead>
                        <tr id="header">
                            <th>Departure Airport</th>
                            <th>Departure Time</th>
                            <th>Arrival Airport</th>
                            <th>Arrival Time</th>
                            <th>Airline</th>
                            <th>Flight Number</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    )
}

export default Flights;