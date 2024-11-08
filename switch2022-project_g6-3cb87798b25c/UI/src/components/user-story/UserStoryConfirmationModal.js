import React, {Fragment, useContext} from "react";
import ReactDOM from "react-dom";
import Card from "../project/Card";
import styles from "../../modalStyles/ConfirmModal.module.css";
import buttonStyles from '../Button.module.css';
import Button from "../Button";
import {BsInfoCircleFill} from "react-icons/bs";

function Backdrop(props) {
    return <div className={styles.backdrop} onClick={props.onCancel}/>;
}

function ModalOverlay(props) {

    const handleSaveClick = () => {
        props.onConfirm();
    }

    return (
        <Card className={styles.usModal}>
            <div className={styles.usContent}>
                <h2 className={styles.usTitle}>
                    <BsInfoCircleFill
                        className={styles.icon}/>Validate <strong>{props.userStory.userStoryCode}</strong> data
                </h2>
                <div className={styles.usInfo}>
                    <div className={styles.body}>
                        <table>
                            <tr>
                                <td>Project Code:</td>
                                <td>{props.userStory.projectCode}</td>
                            </tr>

                            <tr>
                                <td>User Story Code:</td>
                                <td>{props.userStory.userStoryCode}</td>
                            </tr>

                            <tr>
                                <td>Description:</td>
                                <td>{props.userStory.userStoryDescription}</td>
                            </tr>
                        </table>
                    </div>
                </div>
                <div className={styles.actions}>
                    <Button style={buttonStyles.cancel_button} onClick={props.onCancel}>Cancel</Button>
                    <Button style={buttonStyles.confirm_button} onClick={handleSaveClick}>Save</Button>
                </div>
            </div>
        </Card>
    );
}

function UserStoryConfirmationModel(props) {
    return (
        <Fragment>
            {ReactDOM.createPortal(
                <Backdrop title={props.title} userStory={props.newUserStory} onConfirm={props.onConfirm}
                          onCancel={props.onCancel}/>,
                document.getElementById("backdrop-root")
            )}
            {ReactDOM.createPortal(
                <ModalOverlay userStoryFormLabels={props.labels} userStory={props.newUserStory}
                              onConfirm={props.onConfirm}
                              onCancel={props.onCancel}/>,
                document.getElementById("confirmUserStoryModal-root")
            )}
        </Fragment>
    );
}

export default UserStoryConfirmationModel;