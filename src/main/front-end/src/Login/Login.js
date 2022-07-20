import './Login.css';

const Login = () => {
    return (
        <div>
            <h1>Login Page</h1>
            <form>
                <label for="fname">
                First Name:<br></br>
                <input type="text" id="fname" name="fname" />
                <br></br>
                </label>
                <label for="lname">
                Last Name:<br></br>
                <input type="text" id="lname" name="lname" />
                <br></br>
                </label>
                <label for="email">
                Email:<br></br>
                <input type="text" id="email" name="email" />
                <br></br>
                </label>
                <label for="uname">
                Username:<br></br>
                <input type="text" id="uname" name="uname" />
                <br></br>
                </label>
                <label for="pword">
                Password:<br></br>
                <input type="text" id="pword" name="pword" />
                <br></br>
                </label>
            </form>
        </div>
    );
}

export default Login;