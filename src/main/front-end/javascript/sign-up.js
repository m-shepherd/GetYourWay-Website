function handleSubmit(event) {
    event.preventDefault();

    const data = new FormData(event.target);

    var object = {};
    data.forEach((value, key) => object[key] = value);
    var json = JSON.stringify(object);

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "http://localhost:8080/users");
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(json);

}

const form = document.querySelector('form');
form.addEventListener('submit', handleSubmit);
