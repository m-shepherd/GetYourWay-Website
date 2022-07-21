import './Sign-Up.css';

const SignUp = () => {

    function firstNameChange() {
        const firstName = document.querySelector("#firstName")

        const validFirstName = firstName.checkValidity();
        const error = document.querySelector(".firstNameText")

        if (firstName.value.length === 0) {
            error.style.display = "none";
            document.getElementById("firstName").style.borderColor = "lightgrey";
            document.getElementById("firstName").style.borderStyle = "solid";
        } else {

            if (!validFirstName && firstName.value.length >= 15) {
                error.style.display = "block";
                error.innerHTML = 'Too Long';
                document.getElementById("firstName").style.borderColor = "red";
                document.getElementById("firstName").style.borderStyle = "solid";
            } else if (!validFirstName && firstName.value.length <= 1) {
                error.style.display = "block";
                error.innerHTML = 'Too Short';
                document.getElementById("firstName").style.borderColor = "red";
                document.getElementById("firstName").style.borderStyle = "solid";
            } else if (!validFirstName) {
                error.style.display = "block";
                error.innerHTML = 'Only Lower/Upper Case Characters Allowed';
                document.getElementById("firstName").style.borderColor = "red";
                document.getElementById("firstName").style.borderStyle = "solid";
            } else {
                error.style.display = "none";
                document.getElementById("firstName").style.borderColor = "lightgrey";
                document.getElementById("firstName").style.borderStyle = "solid";
            }
        }

    }

    function lastNameChange() {
        const lastName = document.querySelector("#lastName")

        const validLastName = lastName.checkValidity();
        const error = document.querySelector(".lastNameText")

        if (lastName.value.length === 0) {
            error.style.display = "none";
            document.getElementById("lastName").style.borderColor = "lightgrey";
            document.getElementById("lastName").style.borderStyle = "solid";
        } else {

            if (!validLastName && lastName.value.length >= 15) {
                error.style.display = "block";
                error.innerHTML = 'Too Long';
                document.getElementById("lastName").style.borderColor = "red";
                document.getElementById("lastName").style.borderStyle = "solid";
            } else if (!validLastName&& lastName.value.length <= 1) {
                error.style.display = "block";
                error.innerHTML = 'Too Short';
                document.getElementById("lastName").style.borderColor = "red";
                document.getElementById("lastName").style.borderStyle = "solid";
            } else if (!validLastName) {
                error.style.display = "block";
                error.innerHTML = 'Only Lower/Upper Case Characters Allowed';
                document.getElementById("lastName").style.borderColor = "red";
                document.getElementById("lastName").style.borderStyle = "solid";
            } else {
                error.style.display = "none";
                document.getElementById("lastName").style.borderColor = "lightgrey";
                document.getElementById("lastName").style.borderStyle = "solid";
            }
        }
    }

    function emailChange() {
        const email = document.querySelector("#email")

        const validEmail = email.checkValidity();
        const error = document.querySelector(".emailText")

        if (email.value.length === 0) {
            error.style.display = "none";
            document.getElementById("email").style.borderColor = "lightgrey";
            document.getElementById("email").style.borderStyle = "solid";
        } else {
            if (!validEmail) {
                error.style.display = "block";
                document.getElementById("email").style.borderColor = "red";
                document.getElementById("email").style.borderStyle = "solid";
            } else {
                error.style.display = "none";
                document.getElementById("email").style.borderColor = "lightgrey";
                document.getElementById("email").style.borderStyle = "solid";
            }
        }

    }

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
                    <label htmlFor="username">Username:</label>
                    <input type="text" id="username" name="username" required minLength={1} maxLength={32}/><br></br>

                    <label htmlFor="firstName">First name:</label>
                    <input type="text" id="firstName" name="firstName" required pattern="[a-zA-Z]+" minLength={1}
                           maxLength={16} onChange={firstNameChange}/><br></br>
                    <div className="firstNameText" style={{display: "none", color: "red"}}></div>

                    <label htmlFor="lastName">Last name:</label>
                    <input type="text" id="lastName" name="lastName" required pattern="[a-zA-Z]+" minLength={1}
                           maxLength={16} onChange={lastNameChange}/><br></br>
                    <div className="lastNameText" style={{display: "none", color: "red"}}></div>

                    <label htmlFor="email">Email:</label>
                    <input type="text" id="email" name="email" required
                           pattern="^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$" maxLength={64}
                           onChange={emailChange}/><br></br>
                    <div className="emailText" style={{display: "none", color: "red"}}>Invalid Email Address</div>

                    <label htmlFor="password">Password:</label>
                    <input type="text" id="password" name="password" required minLength={1} maxLength={32}/><br></br><br></br>

                    <button type="submit">Submit</button>
                </form>
            </div>
        </div>
    )
}

export default SignUp;