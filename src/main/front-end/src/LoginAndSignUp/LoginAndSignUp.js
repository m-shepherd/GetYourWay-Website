import styles from './LoginInAndSignUp.module.css';
import './LoginAndSignUp.css';
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
                if (serverResponse === '"CREATED"') {
                    navigate('/MainPage')
                }
            }
        };
        xhr.send(json);
    }

    function usernameChange() {
        const username = document.querySelector("#username")

        const validUsername = username.checkValidity();
        const error = document.querySelector("#usernameError")

        if (username.value.length === 0) {
            error.style.display = "none";
            document.getElementById("username").classList.remove("incorrect");
        } else {

            if (!validUsername && username.value.length > 32) {
                error.style.display = "block";
                error.innerHTML = "Has To Be Shorter Than 32 Characters";
                document.getElementById("username").classList.add("incorrect");
            } else if (!validUsername && username.value.length < 8) {
                error.style.display = "block";
                error.innerHTML = "Has To Be Longer Than 8 Characters";
                document.getElementById("username").classList.add("incorrect");
            } else if (!validUsername) {
                error.style.display = "block";
                if (!username.value.charAt(0).match(/[a-z]/i)) {
                    error.innerHTML = "Has To Start With A Letter";
                } else {
                    error.innerHTML = "Can Only Contain Letters, Underscores And Dashes";
                }
                document.getElementById("username").classList.add("incorrect");
            } else {
                error.style.display = "none";
                document.getElementById("username").classList.remove("incorrect");
            }
        }

    }

    function firstNameChange() {
        const firstName = document.querySelector("#firstName")

        const validFirstName = firstName.checkValidity();
        const error = document.querySelector("#firstNameError")

        if (firstName.value.length === 0) {
            error.style.display = "none";
            document.getElementById("firstName").classList.remove("incorrect");
        } else {

            if (!validFirstName && firstName.value.length > 16) {
                error.style.display = "block";
                error.innerHTML = "Has To Be Shorter Than 16 Characters";
                document.getElementById("firstName").classList.add("incorrect");
            } else if (!validFirstName && firstName.value.length <= 1) {
                error.style.display = "block";
                error.innerHTML = "Has To Be Longer Than 1 Characters";
                document.getElementById("firstName").classList.add("incorrect");
            } else if (!validFirstName) {
                error.style.display = "block";
                error.innerHTML = "Can Only Contain Letters";
                document.getElementById("firstName").classList.add("incorrect");
            } else {
                error.style.display = "none";
                document.getElementById("firstName").classList.remove("incorrect");
            }
        }

    }

    function lastNameChange() {
        const lastName = document.querySelector("#lastName")

        const validLastName = lastName.checkValidity();
        const error = document.querySelector("#lastNameError")

        if (lastName.value.length === 0) {
            error.style.display = "none";
            document.getElementById("lastName").classList.remove("incorrect");
        } else {

            if (!validLastName && lastName.value.length >= 15) {
                error.style.display = "block";
                error.innerHTML = "Has To Be Shorter Than 16 Characters";
                document.getElementById("lastName").classList.add("incorrect");
            } else if (!validLastName&& lastName.value.length <= 1) {
                error.style.display = "block";
                error.innerHTML = "Has To Be Longer Than 16 Characters";
                document.getElementById("lastName").classList.add("incorrect");
            } else if (!validLastName) {
                error.style.display = "block";
                error.innerHTML = "Can Only Contain Letters";
                document.getElementById("lastName").classList.add("incorrect");
            } else {
                error.style.display = "none";
                document.getElementById("lastName").classList.remove("incorrect");
            }
        }
    }

    function emailChange() {
        const email = document.querySelector("#email")

        const validEmail = email.checkValidity();
        const error = document.querySelector("#emailError")

        if (email.value.length === 0) {
            error.style.display = "none";
            document.getElementById("email").classList.remove("incorrect");
        } else {
            if (!validEmail) {
                error.style.display = "block";
                error.innerHTML = "Must Follow The Format name@address.xyz"
                document.getElementById("email").classList.add("incorrect");
            } else {
                error.style.display = "none";
                document.getElementById("email").classList.remove("incorrect");
            }
        }

    }

    function passwordChange() {
        const password = document.querySelector("#password")

        const validPassword = password.checkValidity();
        const error = document.querySelector("#passwordError")

        if (password.value.length === 0) {
            error.style.display = "none";
            document.getElementById("password").classList.remove("incorrect");
        } else {
            if (!validPassword && password.value.length > 32) {
                error.style.display = "block";
                error.innerHTML = "Has To Be Shorter Than 32 Characters";
            } else if (!validPassword && password.value.length < 8) {
                error.style.display = "block";
                error.innerHTML = "Has To Be Longer Than 8 Characters";
            } else if (!validPassword) {
                error.style.display = "block";
                error.innerHTML = "Must Contain At Least 1 Letter And 1 Number"
                document.getElementById("password").classList.add("incorrect");
            } else {
                error.style.display = "none";
                document.getElementById("password").classList.remove("incorrect");
            }
        }

    }

    function switchToLogin() {
        const loginText = document.querySelector("#loginText");
        const loginForm = document.querySelector("#loginForm");
        loginForm.style.marginLeft = "0%";
        loginText.style.marginLeft = "0%";

        const radio = document.querySelector("#login");
        radio.checked = true;

        const sliderTab = document.querySelector("#sliderTab");
        sliderTab.style.left = "0%";

        const loginLabel = document.querySelector("#loginLabel");
        loginLabel.style.color = "#fff";
        loginLabel.style.cursor = "default";
        loginLabel.style.userSelect = "none";

        const signUpLabel = document.querySelector("#signUpLabel");
        signUpLabel.style.color = "#000";
        signUpLabel.style.cursor = "pointer";
    }

    function switchToSignUp() {
        const loginText = document.querySelector("#loginText");
        const loginForm = document.querySelector("#loginForm");
        loginForm.style.marginLeft = "-50%";
        loginText.style.marginLeft = "-50%";

        const radio = document.querySelector("#signup");
        radio.checked = true;

        const sliderTab = document.querySelector("#sliderTab");
        sliderTab.style.left = "50%";

        const signUpLabel = document.querySelector("#signUpLabel");
        signUpLabel.style.color = "#fff";
        signUpLabel.style.cursor = "default";
        signUpLabel.style.userSelect = "none";

        const loginLabel = document.querySelector("#loginLabel");
        loginLabel.style.color = "#000";
        loginLabel.style.cursor = "pointer";
    }


    return (
        <div>
            <div className={styles.wrapper}>
                <div className={styles.title_text}>
                    <div id="loginText" className={styles.title}>Login Form</div>
                    <div className={`${styles.title} ${styles.signup}`}>Sign Up Form</div>
                </div>
                <div className={styles.form_container}>
                    <div className={styles.slide_controls}>
                        <input type="radio" name="slide" id="login" checked onChange={switchToSignUp}/>
                        <input type="radio" name="slide" id="signup" onChange={switchToLogin}/>
                        <label id="loginLabel" htmlFor="login" className={styles.slide}
                               onClick={switchToLogin} style={{cursor: "default"}}>Login</label>
                        <label id="signUpLabel" htmlFor="signup" className={`${styles.slide} ${styles.signup}`}
                               onClick={switchToSignUp}>Sign Up</label>
                        <div id="sliderTab" className={styles.slider_tab}></div>
                    </div>
                    <div className={styles.form_inner}>
                        <form id="loginForm" onSubmit={loginSubmit} method="post" action="localhost:8080/users">
                            <div className={styles.field}>
                                <input type="text" name="username" placeholder="Username" required/>
                            </div>
                            <div className={styles.field}>
                                <input type="password" name="password" placeholder="Password" required/>
                            </div>
                            <div className={styles.pass_link} onClick={() => navigate('/ResetPassword')}><a href="#">Forgot Password?</a></div>
                            <div className={`${styles.field} ${styles.btn}`}>
                                <div className={styles.btn_layer}></div>
                                <input type="submit" value="Login"/>
                            </div>

                            <div className={styles.signup_link}>Not A Member? <a href="#" onClick={switchToSignUp}>Sign Up Now</a></div>
                        </form>
                        <form className={styles.signup} onSubmit={signUpSubmit} method="post" action="localhost:8080/users">
                            <div className={styles.field}>
                                <input type="text" id="username" name="username" required
                                       pattern="^[A-Za-z][A-Za-z0-9_-]{7,31}$" placeholder="Username"
                                       onChange={usernameChange}/>
                            </div>
                            <div id="usernameError" className={styles.error} style={{display: 'none', textAlign: 'center'}}></div>

                            <div className={styles.field}>
                                <input type="text" id="firstName" name="firstName" required pattern="[a-zA-Z]{1,16}$"
                                       placeholder="First Name" onChange={firstNameChange}/>
                            </div>
                            <div id="firstNameError" className={styles.error} style={{display: 'none', textAlign: 'center'}}></div>

                            <div className={styles.field}>
                                <input type="text" id="lastName" name="lastName" required pattern="[a-zA-Z]{1,16}$"
                                       placeholder="Last Name" onChange={lastNameChange}/>
                            </div>
                            <div id="lastNameError" className={styles.error} style={{display: 'none', textAlign: 'center'}}></div>
                            <div className={styles.field}>
                                <input type="text" id="email" name="email" required
                                       pattern="^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$"
                                       placeholder="Email Address" maxLength={64} onChange={emailChange}/>
                            </div>
                            <div id="emailError" className={styles.error} style={{display: 'none', textAlign: 'center'}}></div>
                            <div className={styles.field}>
                                <input type="password" id="password" name="password" placeholder="Password" required
                                       pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,32}$" onChange={passwordChange}/>
                            </div>
                            <div id="passwordError" className={styles.error} style={{display: 'none', textAlign: 'center'}}></div>
                            <div className={`${styles.field} ${styles.btn}`}>
                                <div className={styles.btn_layer}></div>
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