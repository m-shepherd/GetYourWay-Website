import './Sign-Up.css';

const SignUp = () => {

    function handleSubmit(event) {
        event.preventDefault();

        const data = new FormData(event.target);

        const object = {};
        data.forEach((value, key) => object[key] = value);
        const json = JSON.stringify(object);

        const xhr = new XMLHttpRequest();
        xhr.open("POST", "http://localhost:8080/users", true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4)  {
                const serverResponse = xhr.responseText;
                console.log(serverResponse);
                // if (serverResponse === '"CREATED"') {
                //     window.location.replace("index.html")
                // }
            }
        };
        xhr.send(json);

    }

    return (
        <div>
            <h1>Sign Up Page</h1>
            <div className="container">
                <form id="signup" onSubmit={handleSubmit} method="post" action="localhost:8080/users">
                    <label htmlFor="username">Username:</label><br></br>
                    <input type="text" id="username" name="username" required/><br></br>
                        <label htmlFor="firstName">First name:</label><br></br>
                        <input type="text" id="firstName" name="firstName" required pattern="[a-zA-Z]+"/><br></br>
                            <label htmlFor="lastName">Last name:</label><br></br>
                            <input type="text" id="lastName" name="lastName" required pattern="[a-zA-Z]+"/><br></br>
                                <label htmlFor="email">Email:</label><br></br>
                                <input type="text" id="email" name="email" required/><br></br>
                                    <label htmlFor="password">Password:</label><br></br>
                                    <input type="text" id="password" name="password" required/><br></br><br></br>
                                        <button type="submit">Submit</button>
                </form>
            </div>
        </div>
    )
}

export default SignUp;