function handleSubmit(event) {
    event.preventDefault();

    const data = new FormData(event.target);

    var object = {};
    data.forEach((value, key) => object[key] = value);
    var json = JSON.stringify(object);

    const xhr = new XMLHttpRequest();
    xhr.open("POST", "http://localhost:8080/users", true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4)  {
            var serverResponse = xhr.responseText;
            if (serverResponse === '"CREATED"') {
                window.location.replace("index.html")
            }
        }
    };
    xhr.send(json);

}

const form = document.querySelector('form');
form.addEventListener('submit', handleSubmit);
