import { useContext } from "react";
import Navbar from "./components/Navbar";
import MainRoute from "./routes/MainRoute";
import AppContext from "./context/AppContext";
import './index.css';
import { BrowserRouter as Router} from "react-router-dom";
import Footer from "./components/Footer";

function App() {
    const { state, dispatch } = useContext(AppContext);
    const { nav } = state;
    const { menus } = nav;
    return (
        <Router>
            <div className="wrapper">
                <div className="content">
                    <Navbar className="nav-menu" items={menus} dispatch={dispatch} />
                    <MainRoute />
                </div>
                <Footer/>
            </div>

        </Router>

    );
}

export default App;
