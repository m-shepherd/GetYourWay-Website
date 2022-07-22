import styles from './ResetPassword.module.css';
import './ResetPassword.css'
import {useNavigate} from "react-router-dom";


const ResetPassword = () =>  {

    let navigate = useNavigate();

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
                error.innerHTML = "Email Must Follow The Format name@address.xyz"
                document.getElementById("email").classList.add("incorrect");
            } else {
                error.style.display = "none";
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
            <div className={styles.wrapper}>
                <div className={styles.title_text}>
                    <div className={styles.title}>Reset Your Password</div>
                </div>
                <div className={styles.form_container}>
                    <div className={styles.form_inner}>
                        <form method="post">
                            <div className={styles.field}>
                                <input type="text" id="email" required
                                       pattern="^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$"
                                       placeholder="Email Address" maxLength={64} onChange={emailChange}/>
                            </div>
                            <div id="emailError" className={styles.error} style={{display: 'none', textAlign: 'center'}}></div>
                            <div className={`${styles.field} ${styles.btn}`}>
                                <div className={styles.btn_layer}></div>
                                <input type="submit" value="Send Recovery Email" onSubmit={submitEmail}/>
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