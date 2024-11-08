import React, {Fragment, useContext} from "react";
import ReactDOM from "react-dom";
import styles from "../../modalStyles/ErrorModalStyle.module.css";
import Card from "./Card.js";
import Button from "../Button";
import buttonStyles from "../Button.module.css";
import AppContext from "../../context/AppContext";
import {setProjectFailure} from "../../context/Actions";
import {AiFillAlert} from "react-icons/ai";


function Backdrop(props) {
    return <div className={styles.backdrop} onClick={props.onConfirm} />;
}

function ModalOverlay() {

    const {state, dispatch} = useContext(AppContext);
    const {postProjectError} = state;

    function confirmHandler() {
        setProjectFailure(dispatch, false);
    }

    return (
        <Card className={styles.modal}>
            <div className={styles.content}>
                <h2><AiFillAlert className={styles.icon}/>Something went wrong</h2>
                <p>{state.applicationState.error}</p>
            </div>
            <footer className={styles.actions}>
                <Button style={buttonStyles.button} onClick={confirmHandler}>Okay</Button>
            </footer>
        </Card>
    );
}

function FailureMessageModal(props) {
    return (
        <Fragment>
            {ReactDOM.createPortal(
                <Backdrop onConfirm={props.onConfirm} />,
                document.getElementById("backdrop-root")
            )}
            {ReactDOM.createPortal(
                <ModalOverlay/>,
                document.getElementById("failureMessageModal-root")
            )}
        </Fragment>
    );
}

export default FailureMessageModal;