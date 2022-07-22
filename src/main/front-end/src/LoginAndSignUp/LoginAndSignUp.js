import styles from './LoginInAndSignUp.module.css';
import "./LoginAndSignUp.css";

const LoginAndSignUp = () => {

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
                // if (serverResponse === '"OK"') {
                //     window.location.replace("index.html")
                // }
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
        const loginText = document.querySelector("#loginText");
        const loginForm = document.querySelector("#loginForm");
        loginForm.style.marginLeft = "0%";
        loginText.style.marginLeft = "0%";

        const radio = document.querySelector("#login");
        radio.checked = true;
    }

    function switchToSignUp() {
        const loginText = document.querySelector("#loginText");
        const loginForm = document.querySelector("#loginForm");
        loginForm.style.marginLeft = "-50%";
        loginText.style.marginLeft = "-50%";

        const radio = document.querySelector("#signup");
        radio.checked = true;
    }

    return (
        <div>
            <div className={styles.wrapper}>
                <div className={styles.title_text}>
                    <div id="loginText" className={`${styles.title} ${styles.login}`}>Login Form</div>
                    <div className={`${styles.title} ${styles.signup}`}>Sign Up Form</div>
                </div>
                <div className={styles.form_container}>
                    <div className={styles.slide_controls}>
                        <input type="radio" name="slide" id="login" checked onChange={switchToSignUp}/>
                        <input type="radio" name="slide" id="signup" onChange={switchToLogin}/>
                        <label htmlFor="login" className={`${styles.slide} ${styles.login}`} onClick={switchToLogin}>Login</label>
                        <label htmlFor="signup" className={`${styles.slide} ${styles.signup}`} onClick={switchToSignUp}>Sign Up</label>
                        <div id="sliderTab" className={styles.slider_tab}></div>
                    </div>
                    <div className={styles.form_inner}>
                        <form id="loginForm" className={styles.login} onSubmit={loginSubmit} method="post" action="localhost:8080/users">
                            <div className={styles.field}>
                                <input type="text" name="username" placeholder="Username" required/>
                            </div>
                            <div className={styles.field}>
                                <input type="password" name="password" placeholder="Password" required/>
                            </div>
                            <div className={styles.pass_link}><a href="#">Forgot Password?</a></div>
                            <div className={`${styles.field} ${styles.btn}`}>
                                <div className={styles.btn_layer}></div>
                                <input type="submit" value="Login"/>
                            </div>
                            <div className={styles.signup_link}>Not A Member? <a onClick={switchToSignUp}>Sign Up Now</a></div>
                        </form>
                        <form className={styles.signup} onSubmit={signUpSubmit} method="post" action="localhost:8080/users">
                            <div className={styles.field}>
                                <input type="text" id="username" name="username" required
                                       pattern="^[A-Za-z][A-Za-z0-9_-]{7,31}$" placeholder="Username"
                                       onChange={usernameChange}/>
                            </div>
                            <div className={styles.field}>
                                <input type="text" id="firstName" name="firstName" required pattern="[a-zA-Z]{1,16}$"
                                       placeholder="First Name" onChange={firstNameChange}/>
                            </div>
                            <div className={styles.field}>
                                <input type="text" id="lastName" name="lastName" required pattern="[a-zA-Z]{1,16}$"
                                       placeholder="Last Name" onChange={lastNameChange}/>
                            </div>
                            <div className={styles.field}>
                                <input type="text" id="email" name="email" required
                                       pattern="^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$"
                                       placeholder="Email Address" maxLength={64} onChange={emailChange}/>
                            </div>
                            <div className={styles.field}>
                                <input type="password" id="password" name="password" placeholder="Password" required
                                       pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,32}$" onChange={passwordChange}/>
                            </div>
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