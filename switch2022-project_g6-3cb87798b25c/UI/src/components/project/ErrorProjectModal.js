import React, {Fragment} from "react";
import ReactDOM from "react-dom";
import styles from "../../modalStyles/ErrorModalStyle.module.css";
import Card from "./Card.js";
import Button from "../Button";
import buttonStyles from "../Button.module.css";
import {AiFillAlert} from "react-icons/ai";

function Backdrop(props) {
    return <div className={styles.backdrop} onClick={props.onConfirm}/>;
}

function ModalOverlay(props) {
    return (
        <Card className={styles.modal}>
            <div className={styles.content}>
                <h2> <AiFillAlert className={styles.icon}/>{props.title}</h2>
                <p>{props.message}</p>
            </div>
            <footer className={styles.actions}>
                <Button style={buttonStyles.button} onClick={props.onConfirm}>Okay</Button>
            </footer>
        </Card>
    );
}

function ErrorProjectModal(props) {
    return (
        <Fragment>
            {ReactDOM.createPortal(
                <Backdrop onConfirm={props.onConfirm}/>,
                document.getElementById("backdrop-root")
            )}
            {ReactDOM.createPortal(
                <ModalOverlay
                    title={props.title}
                    message={props.message}
                    onConfirm={props.onConfirm}
                />,
                document.getElementById("errorProjectModal-root")
            )}
        </Fragment>
    );
}

export default ErrorProjectModal;
