import Card from "../project/Card";
import styles from "../../modalStyles/ErrorModalStyle.module.css";
import Button from "../Button";
import buttonStyles from "../Button.module.css";
import React, {Fragment} from "react";
import ReactDOM from "react-dom";

function Backdrop(props) {
    return <div className={styles.backdrop} onClick={props.onClick} />;
}

function Modal({onConfirm, onClose}) {

    const handleConfirm = () => {
        onConfirm();
    };

    const handleCancel = () => {
        onClose();
    };

    return (
        <Card className={styles.modal}>
            <div className={styles.content}>
                <p>Are you sure you want to close the Sprint?</p>
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

function CloseSprintConfirmationModal(props) {

    if (!props.showModal) {
        return null
    }

    return (
        <Fragment>
            {ReactDOM.createPortal(
                <Backdrop onConfirm={props.onConfirm} />,
                document.getElementById("backdrop-root")
            )}

            {ReactDOM.createPortal(
                <Modal
                    message={props.message}
                    onConfirm={props.onConfirm}
                    onClose={props.onClose}

                />,
                document.getElementById("closeSprintConfirmationModal-root")
            )}
        </Fragment>
    );
}

export default CloseSprintConfirmationModal;
