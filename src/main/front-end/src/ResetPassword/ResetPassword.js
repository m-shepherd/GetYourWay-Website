import styles from './ResetPassword.module.css';
import './ResetPassword.css'
import {useNavigate} from "react-router-dom";


const ResetPassword = () =>  {

    let navigate = useNavigate();

    function emailChange() {
        const email = document.querySelector("#email");

        const validEmail = email.checkValidity();
        const error = document.querySelector("#emailError")

        if (email.value.length === 0) {
            error.style.display = "none";
            document.getElementById("email").classList.remove("incorrect");
        } else {
            if (!validEmail) {
                error.style.display = "block";
                error.innerHTML = "Email Must Follow The Format name@address.xyz"
                document.getElementById("email").classList.add("incorrect");
            } else {
                error.style.display = "none";
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
            xhr.open("POST", "http://localhost:8080/email/sendMail");
            xhr.setRequestHeader("Content-Type", "application/json");
            xhr.send(JSON.stringify(emailSettings));

    }

    function checkEmailIsValid(email){
        const xhr = new XMLHttpRequest();
        xhr.open("GET", "http://localhost:8080/email/getUserByEmail?email=" + email, true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.send();
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4)  {
                const serverResponse = xhr.responseText;
                if (serverResponse === '"OK"') {
                    sendEmail(email)
                }
            }
        };
    }


    function submitEmail(event) {
        event.preventDefault();

        const email = document.querySelector('#email').value;

        checkEmailIsValid(email);
    }


    return (
        <div>
            <div className={styles.wrapper}>
                <div className={styles.title_text}>
                    <div className={styles.title}>Reset Your Password</div>
                </div>
                <div className={styles.form_container}>
                    <div className={styles.form_inner}>
                        <form method="post" onSubmit={submitEmail}>
                            <div className={styles.field}>
                                <input type="text" id="email" name="email" required
                                       pattern="^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$"
                                       placeholder="Email Address" maxLength={64} onChange={emailChange}/>
                            </div>
                            <div id="emailError" className={styles.error} style={{display: 'none', textAlign: 'center'}}></div>
                            <div className={`${styles.field} ${styles.btn}`}>
                                <div className={styles.btn_layer}></div>
                                <input type="submit" value="Send Recovery Email"/>
                            </div>
                            <div className={styles.pass_link} onClick={() => navigate('/')}><a href="#">Back To Login</a></div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ResetPassword;