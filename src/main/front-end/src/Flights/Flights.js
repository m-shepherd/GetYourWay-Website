import './Flights.css';

const Flights = () => {

    function getFlights() {
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
                for(const flight in flights) {
                    const row = flightTable.insertRow(-1)
                    row.setAttribute('data-href', '#');
                    row.setAttribute('onClick', '{clicked}');
                    let i = 0;
                    for (const key in flights[flight]) {
                        if (i !== 0) {
                            const cell = row.insertCell(i - 1);
                            cell.innerHTML = flights[flight][key];
                        }
                        i ++;
                    }
                }
                makeRowsClickable();
            }
        };
        xhr.send();
    }

    function makeRowsClickable() {
        const table = document.getElementById("table");
        const rows = table.getElementsByTagName("tr");
        for (let i = 0; i < rows.length; i++) {
            const currentRow = table.rows[i];
            const createClickHandler = function() {
                return function(event) {
                    const isClicked = event.target.parentElement.classList.contains('clicked');
                    const clickedItems = document.getElementsByClassName('clicked');
                    for (let i = 0; i < clickedItems.length; i++) {
                        clickedItems[i].classList.remove('clicked');
                    }
                    if (isClicked) {
                        event.target.parentElement.classList.remove('clicked');
                    } else {
                        event.target.parentElement.classList.toggle('clicked');
                    }
                    showConfirmButton();
                };
            };
            currentRow.onclick = createClickHandler();
        }
    }

    function showConfirmButton() {
        let clicked = false;
        const clickedItems = document.getElementsByClassName('clicked');
        for (let i = 0; i < clickedItems.length; i++) {
            if (clickedItems[i].classList.contains('clicked')) {
                clicked = true;
            }
        }
        const destination = document.querySelector("#destination");
        if (clicked) {

            destination.style.display = "block";
        } else {
            destination.style.display = "none";
        }
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
                <div id="destination" className="field btn" style={{display: "none"}}>
                    <div className="btn-layer"></div>
                    <input type="submit" value="Confirm Flights"/>
                </div>
            </div>
        </div>
    )
}

export default Flights;