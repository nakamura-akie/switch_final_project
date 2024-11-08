import Card from "../project/Card";
import styles from "../../modalStyles/ErrorModalStyle.module.css";
import Button from "../Button";
import buttonStyles from "../Button.module.css";
import React, {Fragment, useContext} from "react";
import AppContext from "../../context/AppContext";
import ReactDOM from "react-dom";
import {AiFillAlert} from "react-icons/ai";
import {useNavigate} from "react-router-dom";
import {setSprintCreationFailure} from "../../context/Actions";




function Backdrop(props) {
    return <div className={styles.backdrop} onClick={props.onConfirm} />;
}

function Modal() {
 const {state, dispatch} = useContext(AppContext);
    const navigate = useNavigate();

function confirmHandler() {
        setSprintCreationFailure(dispatch, false);
        navigate(-1);
    }

        return (
        <Card className={styles.modal}>
            <div className={styles.content}>
                <h2><AiFillAlert className={styles.icon}/>Something went wrong</h2>
                               <p>{state.applicationState.sprintMessage}</p>
            </div>
            <footer className={styles.actions}>
                <Button style={buttonStyles.button} onClick={confirmHandler}>Okay</Button>
            </footer>
        </Card>
    );
}

function FailureSprintCreationModal(props) {
    return (
        <Fragment>
            {ReactDOM.createPortal(
                <Backdrop onConfirm={props.onConfirm} />,
                document.getElementById("backdrop-root")
            )}
            {ReactDOM.createPortal(
                <Modal/>,
                document.getElementById("errorSprintCreationModal-root")
            )}
        </Fragment>
    );
}

export default FailureSprintCreationModal;
