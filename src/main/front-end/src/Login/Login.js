import './Login.css';

const Login = () => {

    function handleSubmit(event) {
        event.preventDefault();

        const data = new FormData(event.target);

        const object = {};
        data.forEach((value, key) => object[key] = value);
        const json = JSON.stringify(object);
        const jsonData = JSON.parse(json);

        const xhr = new XMLHttpRequest();
        xhr.open("POST", "http://localhost:8080/users/" + jsonData.username + "/" + jsonData.password, true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4)  {
                var serverResponse = xhr.responseText;
                console.log(serverResponse);
                // if (serverResponse === '"OK"') {
                //     window.location.replace("index.html")
                // }
            }
        };
        xhr.send();

    }

    return (
        <div>
            <h1>Log In Page</h1>
            <div className="container">
                <form id="login" onSubmit={handleSubmit} method="post" action="localhost:8080/users">
                    <label htmlFor="username">Username:</label><br></br>
                    <input type="text" id="username" name="username"/><br></br>
                        <label htmlFor="password">Password:</label><br></br>
                        <input type="text" id="password" name="password"/><br></br><br></br>
                            <button type="submit">Submit</button>
                </form>
            </div>
        </div>
    );
}

export default Login;