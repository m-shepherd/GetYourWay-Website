import './ResetPassword.css';
import {useNavigate} from "react-router-dom";
import axios from 'axios';


const ResetPassword = () =>  {

    let navigate = useNavigate();

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

    function submitEmail(event) {
        event.preventDefault();
    }

    function sendEmail(event){
        event.preventDefault();

    }

    return (
        <div>
            <div className="wrapper">
                <div className="title-text">
                    <div className="title login">Reset Your Password</div>
                </div>
                <div className="form-container">
                    <div className="form-inner">
                        <form className="login" method="post">
                            <div className="field">
                                <input type="text" name="email" placeholder="Email" required
                                       pattern="^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$"
                                       placeholder="Email Address" maxLength={64} onChange={emailChange}/>
                            </div>
                            <div className="field btn">
                                <div className="btn-layer"></div>
                                <input type="submit" value="Send Recovery Email" onSubmit={submitEmail}/>
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