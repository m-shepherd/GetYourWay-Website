function handleSubmit(event) {
    event.preventDefault();

    const data = new FormData(event.target);

    const object = {};
    data.forEach((value, key) => object[key] = value);
    const json = JSON.stringify(object);
    const jsonData = JSON.parse(json);

    console.log("http://localhost:8080/users/" + jsonData.username + "/" + jsonData.password)

    const xhr = new XMLHttpRequest();
    xhr.open("POST", "http://localhost:8080/users/" + jsonData.username + "/" + jsonData.password, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4)  {
            var serverResponse = xhr.responseText;
            if (serverResponse === '"OK"') {
                window.location.replace("index.html")
            }
        }
    };
    xhr.send();

}

const form = document.querySelector('form');
form.addEventListener('submit', handleSubmit);
