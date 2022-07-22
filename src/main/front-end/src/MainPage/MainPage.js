import './MainPage.css'
import {useNavigate} from "react-router-dom";

const MainPage = () => {

    let navigate = useNavigate();

    return (
        <div>
            <h1>Main Page</h1>
            <div className="signup-link" onClick={() => navigate('/')}><a>Log out</a>
            </div>
        </div>
    )
}

export default MainPage;