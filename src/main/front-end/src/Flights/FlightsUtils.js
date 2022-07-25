export function getFlights(event) {
    event.preventDefault();
    const xhr = new XMLHttpRequest();
    xhr.open("GET", "http://localhost:8080/flights/2022-07-13&LHR&FRA", true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4)  {
            const flights = JSON.parse(xhr.responseText);
            console.log(flights);
            const flightTable = document.querySelector("#flightTable");
            const flightData = document.querySelector("#flightData");
            const dataTitle = document.querySelector("#dataTitle");
            flightData.style.display = "block";
            dataTitle.style.display = "block";
            for(const flight in flights) {
                const row = flightTable.insertRow(-1)
                // row.setAttribute('data-href', '#');
                // // row.setAttribute('onClick', '{clicked}');
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

export function makeRowsClickable() {
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

export function showConfirmButton() {
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