import styles from'./MainPage.module.css'
import {useNavigate} from "react-router-dom";

const MainPage = () => {

    let navigate = useNavigate();

    return (
        <div>
            <div className={styles.wrapper}>
                <div className={styles.title_text}>
                    <div className={styles.title}>Main Page</div>
                </div>
                <div className={styles.pass_link} onClick={() => navigate('/')}><a href="">Log Out</a></div>
                <div className={styles.pass_link} onClick={() => navigate('/Flights')}><a href="">Find Flights</a></div>
            </div>
        </div>
    )
}

export default MainPage;