import React, {Fragment, useContext} from "react";
import ReactDOM from "react-dom";
import styles from "../../modalStyles/SuccessfulModalStyle.module.css"
import Card from "../project/Card";
import Button from "../Button";
import buttonStyles from "../Button.module.css";
import AppContext from "../../context/AppContext";
import {setUserStorySuccess} from "../../context/Actions";
import {BsFillCheckCircleFill} from "react-icons/bs";
import {useNavigate} from "react-router-dom";


function Backdrop(props) {
    return <div className={styles.backdrop} onClick={props.onConfirm}/>;
}

function ModalOverlay(props) {
    return (
        <Card className={styles.modal}>
            <div className={styles.content}>
                <p><BsFillCheckCircleFill className={styles.icon}/>User Story Successfully Added</p>
            </div>
            <footer className={styles.actions}>
                <Button style={buttonStyles.button} onClick={props.onConfirm}>Okay</Button>
            </footer>
        </Card>
    );
}

function UserStorySuccessModel() {
    const {dispatch} = useContext(AppContext);
    const navigate = useNavigate();

    function confirmHandler() {
        setUserStorySuccess(dispatch, false);
        navigate(-1);
    }

    return (
        <Fragment>
            {ReactDOM.createPortal(
                <Backdrop onConfirm={confirmHandler}/>,
                document.getElementById("backdrop-root")
            )}
            {ReactDOM.createPortal(
                <ModalOverlay onConfirm={confirmHandler}/>,
                document.getElementById("userStorySuccessModel-root")
            )}
        </Fragment>
    );
}

export default UserStorySuccessModel;
