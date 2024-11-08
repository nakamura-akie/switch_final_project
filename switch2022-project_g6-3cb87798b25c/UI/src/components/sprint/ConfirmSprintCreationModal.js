import Card from "../project/Card";
import styles from "../../modalStyles/ConfirmModal.module.css";
import Button from "../Button";
import buttonStyles from "../Button.module.css";
import React, {Fragment} from "react";
import ReactDOM from "react-dom";

function Backdrop(props) {
    return <div className={styles.backdrop} onClick={props.onClick} />;
}

function Modal(props) {


    const handleConfirm = () => {
        props.onConfirm();
    };

    const handleCancel = () => {
        props.onCancel();
    };

    return (
        <Card className={styles.modal}>
            <div className={styles.content}>
                <p>Are you sure you want to create this Sprint?</p>
                <Button style={buttonStyles.confirm_button} onClick={handleConfirm}>
                    Ok
                </Button>
                <Button style={buttonStyles.cancel_button} onClick={handleCancel}>
                    Cancel
                </Button>

            </div>
        </Card>
    );
}

function ConfirmSprintCreationModal(props) {
    return (
        <Fragment>
            {ReactDOM.createPortal(
                <Backdrop newSprint={props.newSprint} onConfirm={props.onConfirm} />,
                document.getElementById("backdrop-root")
            )}

            {ReactDOM.createPortal(
                <Modal
                    newSprint={props.newSprint}
                    message={props.message}
                    onConfirm={props.onConfirm}
                    onCancel={props.onCancel}

                />,
                document.getElementById("confirmSprintCreationModal-root")
            )}
        </Fragment>
    );
}

export default ConfirmSprintCreationModal;
