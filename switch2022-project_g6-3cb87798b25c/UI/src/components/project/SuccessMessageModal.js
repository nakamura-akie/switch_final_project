import React, {Fragment, useContext} from "react";
import ReactDOM from "react-dom";
import styles from "../../modalStyles/SuccessfulModalStyle.module.css";
import Card from "./Card.js";
import Button from "../Button";
import buttonStyles from "../Button.module.css";
import AppContext from "../../context/AppContext";
import {setProjectSuccess} from "../../context/Actions";
import {BsFillCheckCircleFill} from "react-icons/bs";


function Backdrop(props) {
    return <div className={styles.backdrop} onClick={props.onConfirm}/>;
}

function ModalOverlay(props) {
    return (
        <Card className={styles.modal}>
            <div className={styles.content}>
                <p><BsFillCheckCircleFill className={styles.icon}/> Project Successfully Added </p>
            </div>
            <footer className={styles.actions}>
                <Button style={buttonStyles.button} onClick={props.onConfirm}>Okay</Button>
            </footer>
        </Card>
    );
}

function SuccessMessageModal() {
    const {dispatch} = useContext(AppContext);

    function confirmHandler() {
        setProjectSuccess(dispatch, false);
    }

    return (
        <Fragment>
            {ReactDOM.createPortal(
                <Backdrop onConfirm={confirmHandler}/>,
                document.getElementById("backdrop-root")
            )}
            {ReactDOM.createPortal(
                <ModalOverlay onConfirm={confirmHandler}/>,
                document.getElementById("successMessageModal-root")
            )}
        </Fragment>
    );
}

export default SuccessMessageModal;
