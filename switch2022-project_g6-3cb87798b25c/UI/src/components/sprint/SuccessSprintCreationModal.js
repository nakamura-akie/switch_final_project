import Card from "../project/Card";
import styles from "../../modalStyles/SuccessfulModalStyle.module.css";
import Button from "../Button";
import buttonStyles from "../Button.module.css";
import React, {Fragment, useContext} from "react";
import AppContext from "../../context/AppContext";
import ReactDOM from "react-dom";
import {BsFillCheckCircleFill} from "react-icons/bs";
import {setSprintCreationSuccess} from "../../context/Actions";
import {useNavigate} from "react-router-dom";



function Backdrop(props) {
    return <div className={styles.backdrop} onClick={props.onConfirm} />;
}

function Modal(props) {
    return (
        <Card className={styles.modal}>
            <div className={styles.content}>
                <p><BsFillCheckCircleFill className={styles.icon}/>Sprint Successfully created</p>
            </div>
            <footer className={styles.actions}>
                <Button style={buttonStyles.button} onClick={props.onConfirm}>Ok</Button>
            </footer>
        </Card>
    );
}

function SuccessSprintCreationModal(props) {
    const {dispatch} = useContext(AppContext);
    const navigate = useNavigate();

    function confirmHandler() {
            setSprintCreationSuccess(dispatch, false);
            navigate(-1);
        }

    return (
        <Fragment>
            {ReactDOM.createPortal(
                <Backdrop onConfirm={props.onConfirm} />,
                document.getElementById("backdrop-root")
            )}
            {ReactDOM.createPortal(
                <Modal onConfirm={confirmHandler}/>,
                document.getElementById("confirmationMessageModal-root")
            )}
        </Fragment>
    );
}

export default SuccessSprintCreationModal;
