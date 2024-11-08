import React, {Fragment, useContext} from "react";
import ReactDOM from "react-dom";
import styles from "../../modalStyles/ErrorModalStyle.module.css";
import Card from "../project/Card";
import Button from "../Button";
import buttonStyles from "../Button.module.css";
import AppContext from "../../context/AppContext";
import {setUserStoryFailure} from "../../context/Actions";
import {AiFillAlert} from "react-icons/ai";
import {useNavigate} from "react-router-dom";


function Backdrop(props) {
    return <div className={styles.backdrop} onClick={props.onConfirm} />;
}

function ModalOverlay() {

    const {state, dispatch} = useContext(AppContext);
    const navigate = useNavigate();

    function confirmHandler() {
        setUserStoryFailure(dispatch, false);
        navigate(-1);
    }

    return (
        <Card className={styles.modal}>
            <div className={styles.content}>
                <h2><AiFillAlert className={styles.icon}/>Something went wrong</h2>
                <p>{state.applicationState.userStoryMessage}</p>
            </div>
            <footer className={styles.actions}>
                <Button style={buttonStyles.button} onClick={confirmHandler}>Okay</Button>
            </footer>
        </Card>
    );
}

function FailureUserStoryModal(props) {
    return (
        <Fragment>
            {ReactDOM.createPortal(
                <Backdrop onConfirm={props.onConfirm} />,
                document.getElementById("backdrop-root")
            )}
            {ReactDOM.createPortal(
                <ModalOverlay/>,
                document.getElementById("failureUserStoryModal-root")
            )}
        </Fragment>
    );
}

export default FailureUserStoryModal;