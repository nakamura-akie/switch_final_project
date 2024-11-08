import React, {Fragment, useContext} from "react";
import ReactDOM from "react-dom";
import styles from "../../modalStyles/ErrorModalStyle.module.css";
import Card from "../project/Card";
import Button from "../Button";
import buttonStyles from "../Button.module.css";
import AppContext from "../../context/AppContext";
import {setResourceFailure} from "../../context/Actions";
import {AiFillAlert} from "react-icons/ai";


function Backdrop(props) {
    return <div className={styles.backdrop} onClick={props.onConfirm} />;
}

function ModalOverlay() {

    const {state, dispatch} = useContext(AppContext);

    function confirmHandler() {
        setResourceFailure(dispatch, false);
    }

    return (
        <Card className={styles.modal}>
            <header className={styles.header}>
                <h2><AiFillAlert className={styles.icon}/>Something went wrong</h2>
            </header>

            <div className={styles.content}>
                <p>{state.applicationState.error}</p>
            </div>
            <footer className={styles.actions}>
                <Button style={buttonStyles.button} onClick={confirmHandler}>Okay</Button>
            </footer>
        </Card>
    );
}

function ResourceFailureMessageModal(props) {
    return (
        <Fragment>
            {ReactDOM.createPortal(
                <Backdrop onConfirm={props.onConfirm} />,
                document.getElementById("backdrop-root")
            )}
            {ReactDOM.createPortal(
                <ModalOverlay/>,
                document.getElementById("resourceFailureMessageModal-root")
            )}
        </Fragment>
    );
}

export default ResourceFailureMessageModal;