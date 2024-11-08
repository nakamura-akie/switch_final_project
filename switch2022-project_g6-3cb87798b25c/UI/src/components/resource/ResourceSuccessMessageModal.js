import React, {Fragment, useContext} from "react";
import ReactDOM from "react-dom";
import styles from "../../modalStyles/SuccessfulModalStyle.module.css"
import Card from "../project/Card";
import Button from "../Button";
import buttonStyles from "../Button.module.css";
import AppContext from "../../context/AppContext";
import {setResourceSuccess} from "../../context/Actions";
import {BsFillCheckCircleFill} from "react-icons/bs";


function Backdrop(props) {
    return <div className={styles.backdrop} onClick={props.onConfirm}/>;
}

function ModalOverlay(props) {
    return (
        <Card className={styles.modal}>
            <div className={styles.content}>
                <p><BsFillCheckCircleFill className={styles.icon}/>Resource Successfully Added</p>
            </div>
            <footer className={styles.actions}>
                <Button style={buttonStyles.button} onClick={props.onConfirm}>Okay</Button>
            </footer>
        </Card>
    );
}

function ResourceSuccessMessageModal(props) {
    const {dispatch} = useContext(AppContext);

    function confirmHandler() {
        setResourceSuccess(dispatch, false);
    }

    return (
        <Fragment>
            {ReactDOM.createPortal(
                <Backdrop onConfirm={confirmHandler}/>,
                document.getElementById("backdrop-root")
            )}
            {ReactDOM.createPortal(
                <ModalOverlay onConfirm={confirmHandler}/>,
                document.getElementById("resourceSuccessMessageModal-root")
            )}
        </Fragment>
    );
}

export default ResourceSuccessMessageModal;
