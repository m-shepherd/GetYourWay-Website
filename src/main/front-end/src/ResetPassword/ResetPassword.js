import './ResetPassword.css';
import {useNavigate} from "react-router-dom";
import axios from 'axios';


const ResetPassword = () =>  {

    let navigate = useNavigate();

    function emailChange() {
        const email = document.querySelector("#email");

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

    function sendEmail(email){
            const emailSettings = {
                "recipient": email,
                "msgBody": "http://localhost:8080/users",
                "subject": "Test email"
            }

            const xhr = new XMLHttpRequest();
            xhr.open("POST", "http://localhost:8080/sendMail");
            xhr.setRequestHeader("Content-Type", "application/json");
            xhr.send(JSON.stringify(emailSettings));

    }

    function checkEmailIsValid(email){
        const userObject = {
            "username" : "placeholder",
            "firstName" : "placeholder",
            "lastName" : "placeholder",
            "email" : email,
            "password" : "placeholder"
        }
        console.log(userObject);
        const userJSON = JSON.stringify(userObject);
        //const userData = JSON.parse(userJSON);
        const xhr = new XMLHttpRequest();
        xhr.open("POST", "http://localhost:8080/users/getUserByEmail", true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.send(userJSON);
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4)  {
                const serverResponse = xhr.responseText;
                console.log(serverResponse);
                if (serverResponse === '"OK"') {
                    sendEmail(email)
                }
            }
        };
    }


    function submitEmail(event) {
        event.preventDefault();

        const data = new FormData(event.target);

        const object = {};
        data.forEach((value, key) => object[key] = value);
        const json = JSON.stringify(object);
        const jsonData = JSON.parse(json);

        checkEmailIsValid(jsonData.email);
    }


    return (
        <div>
            <div className="wrapper">
                <div className="title-text">
                    <div className="title login">Reset Your Password</div>
                </div>
                <div className="form-container">
                    <div className="form-inner">
                        <form className="login" method="post" onSubmit={submitEmail}>
                            <div className="field">
                                <input type="text" id="email" name="email" placeholder="Email" required
                                       pattern="^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$"
                                       placeholder="Email Address" maxLength={64} onChange={emailChange}/>
                            </div>
                            <div className="field btn">
                                <div className="btn-layer"></div>
                                <input type="submit" value="Send Recovery Email"/>
                            </div>
                            <div className="signup-link" onClick={() => navigate('/')}><a>Go Back to Login</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ResetPassword;