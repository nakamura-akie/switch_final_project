import React, {Fragment} from "react";
import ReactDOM from "react-dom";
import styles from "../../modalStyles/ErrorModalStyle.module.css";
import Card from "../project/Card";
import Button from "../Button";
import buttonStyles from "../Button.module.css";
import {AiFillAlert} from "react-icons/ai";

function Backdrop(props) {
    return <div className={styles.backdrop} onClick={props.onConfirm}/>;
}

function ModalOverlay(props) {
    return (
        <Card className={styles.modal}>
            <header className={styles.header}>
                <h2><AiFillAlert className={styles.icon}/>{props.title}</h2>
            </header>

            <div className={styles.content}>
                <p>{props.message}</p>
            </div>
            <footer className={styles.actions}>
                <Button style={buttonStyles.button} onClick={props.onConfirm}>Okay</Button>
            </footer>
        </Card>
    );
}

function ErrorResourceModal(props) {
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
                document.getElementById("errorResourceModal-root")
            )}
        </Fragment>
    );
}

export default ErrorResourceModal;
