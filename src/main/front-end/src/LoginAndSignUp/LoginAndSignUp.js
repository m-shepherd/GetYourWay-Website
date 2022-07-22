import './LoginInAndSignUp.css';
import {useNavigate} from "react-router-dom";

const LoginAndSignUp = () => {

    let navigate = useNavigate();

    function loginSubmit(event) {
        event.preventDefault();

        const data = new FormData(event.target);

        const object = {};
        data.forEach((value, key) => object[key] = value);
        const json = JSON.stringify(object);
        const jsonData = JSON.parse(json);

        console.log(jsonData);

        const xhr = new XMLHttpRequest();
        xhr.open("POST", "http://localhost:8080/users/" + jsonData.username + "/" + jsonData.password, true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4)  {
                const serverResponse = xhr.responseText;
                console.log(serverResponse);
                if (serverResponse === '"OK"') {
                    navigate('/MainPage')
                 }
            }
        };
        xhr.send();
    }

    function signUpSubmit(event) {
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

    function usernameChange() {
        const username = document.querySelector("#username")

        const validUsername = username.checkValidity();
        // const error = document.querySelector(".firstNameText")

        if (username.value.length === 0) {
            // error.style.display = "none";
            document.getElementById("username").classList.remove("incorrect");
        } else {

            if (!username && username.value.length >= 15) {
                // error.style.display = "block";
                // error.innerHTML = 'Too Long';
                document.getElementById("username").classList.add("incorrect");
            } else if (!validUsername && username.value.length <= 1) {
                // error.style.display = "block";
                // error.innerHTML = 'Too Short';
                document.getElementById("username").classList.add("incorrect");
            } else if (!validUsername) {
                console.error("Invalid username");
                // error.style.display = "block";
                // error.innerHTML = 'Only Lower/Upper Case Characters Allowed';
                document.getElementById("username").classList.add("incorrect");
            } else {
                // error.style.display = "none";
                document.getElementById("username").classList.remove("incorrect");
            }
        }

    }

    function firstNameChange() {
        const firstName = document.querySelector("#firstName")

        const validFirstName = firstName.checkValidity();
        // const error = document.querySelector(".firstNameText")

        if (firstName.value.length === 0) {
            // error.style.display = "none";
            document.getElementById("firstName").classList.remove("incorrect");
        } else {

            if (!validFirstName && firstName.value.length >= 15) {
                // error.style.display = "block";
                // error.innerHTML = 'Too Long';
                document.getElementById("firstName").classList.add("incorrect");
            } else if (!validFirstName && firstName.value.length <= 1) {
                // error.style.display = "block";
                // error.innerHTML = 'Too Short';
                document.getElementById("firstName").classList.add("incorrect");
            } else if (!validFirstName) {
                console.error("Invalid firstName");
                // error.style.display = "block";
                // error.innerHTML = 'Only Lower/Upper Case Characters Allowed';
                document.getElementById("firstName").classList.add("incorrect");
            } else {
                // error.style.display = "none";
                document.getElementById("firstName").classList.remove("incorrect");
            }
        }

    }

    function lastNameChange() {
        const lastName = document.querySelector("#lastName")

        const validLastName = lastName.checkValidity();
        // const error = document.querySelector(".lastNameText")

        if (lastName.value.length === 0) {
            // error.style.display = "none";
            document.getElementById("lastName").classList.remove("incorrect");
        } else {

            if (!validLastName && lastName.value.length >= 15) {
                // error.style.display = "block";
                // error.innerHTML = 'Too Long';
                document.getElementById("lastName").classList.add("incorrect");
            } else if (!validLastName&& lastName.value.length <= 1) {
                // error.style.display = "block";
                // error.innerHTML = 'Too Short';
                document.getElementById("lastName").classList.add("incorrect");
            } else if (!validLastName) {
                // error.style.display = "block";
                // error.innerHTML = 'Only Lower/Upper Case Characters Allowed';
                document.getElementById("lastName").classList.add("incorrect");
            } else {
                // error.style.display = "none";
                document.getElementById("lastName").classList.remove("incorrect");
            }
        }
    }

    function emailChange() {
        const email = document.querySelector("#email")

        const validEmail = email.checkValidity();
        // const error = document.querySelector(".emailText")

        if (email.value.length === 0) {
            // error.style.display = "none";
            document.getElementById("email").classList.remove("incorrect");
        } else {
            if (!validEmail) {
                // error.style.display = "block";
                document.getElementById("email").classList.add("incorrect");
            } else {
                // error.style.display = "none";
                document.getElementById("email").classList.remove("incorrect");
            }
        }

    }

    function passwordChange() {
        const password = document.querySelector("#password")

        const validPassword = password.checkValidity();
        // const error = document.querySelector(".emailText")

        if (password.value.length === 0) {
            // error.style.display = "none";
            document.getElementById("password").classList.remove("incorrect");
        } else {
            if (!validPassword) {
                // error.style.display = "block";
                document.getElementById("password").classList.add("incorrect");
            } else {
                // error.style.display = "none";
                document.getElementById("password").classList.remove("incorrect");
            }
        }

    }

    function switchToLogin() {
        const loginText = document.querySelector(".title-text .login");
        const loginForm = document.querySelector("form.login");
        loginForm.style.marginLeft = "0%";
        loginText.style.marginLeft = "0%";

        const radio = document.querySelector("#login");
        radio.checked = true;
    }

    function switchToSignUp() {
        const loginText = document.querySelector(".title-text .login");
        const loginForm = document.querySelector("form.login");
        loginForm.style.marginLeft = "-50%";
        loginText.style.marginLeft = "-50%";

        const radio = document.querySelector("#signup");
        radio.checked = true;
    }


    return (
        <div>
            <div className="wrapper">
                <div className="title-text">
                    <div className="title login">Login Form</div>
                    <div className="title signup">Sign Up Form</div>
                </div>
                <div className="form-container">
                    <div className="slide-controls">
                        <input type="radio" name="slide" id="login" checked onChange={switchToSignUp}/>
                        <input type="radio" name="slide" id="signup" onChange={switchToLogin}/>
                        <label htmlFor="login" className="slide login" onClick={switchToLogin}>Login</label>
                        <label htmlFor="signup" className="slide signup" onClick={switchToSignUp}>Sign Up</label>
                        <div className="slider-tab"></div>
                    </div>
                    <div className="form-inner">
                        <form className="login" onSubmit={loginSubmit} method="post" action="localhost:8080/users">
                            <div className="field">
                                <input type="text" name="username" placeholder="Username" required/>
                            </div>
                            <div className="field">
                                <input type="password" name="password" placeholder="Password" required/>
                            </div>
                                <div className="pass-link" onClick={() => navigate('/ResetPassword')}><a href="#">Forgot Password?</a></div>
                            <div className="field btn">
                                <div className="btn-layer"></div>
                                <input type="submit" value="Login"/>
                            </div>
                            <div className="signup-link">Not A Member? <a onClick={switchToSignUp}>Sign Up Now</a></div>
                        </form>
                        <form className="signup" onSubmit={signUpSubmit} method="post" action="localhost:8080/users">
                            <div className="field">
                                <input type="text" id="username" name="username" required
                                       pattern="^[A-Za-z][A-Za-z0-9_]{7,31}$" placeholder="Username"
                                       onChange={usernameChange}/>
                            </div>
                            <div className="field">
                                <input type="text" id="firstName" name="firstName" required pattern="[a-zA-Z]{1,16}$"
                                       placeholder="First Name" onChange={firstNameChange}/>
                            </div>
                            <div className="field">
                                <input type="text" id="lastName" name="lastName" required pattern="[a-zA-Z]{1,16}$"
                                       placeholder="Last Name" onChange={lastNameChange}/>
                            </div>
                            <div className="field">
                                <input type="text" id="email" name="email" required
                                       pattern="^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$"
                                       placeholder="Email Address" maxLength={64} onChange={emailChange}/>
                            </div>
                            <div className="field">
                                <input type="password" id="password" name="password" placeholder="Password" required
                                       pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,32}$" onChange={passwordChange}/>
                            </div>
                            {/*<div className="field">*/}
                            {/*    <input type="password" placeholder="Confirm Password" required*/}
                            {/*           pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,32}$"/>*/}
                            {/*</div>*/}
                            <div className="field btn">
                                <div className="btn-layer"></div>
                                <input type="submit" value="Sign Up"/>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default LoginAndSignUp;