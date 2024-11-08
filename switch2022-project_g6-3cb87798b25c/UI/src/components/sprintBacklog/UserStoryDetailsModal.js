import React, {Fragment, useContext} from "react";
import AppContext from "../../context/AppContext";
import ReactDOM from "react-dom";
import styles from "../../modalStyles/DetailsModal.module.css"
import Card from "../project/Card";
import {BsInfoCircleFill, BsXCircleFill} from "react-icons/bs";

function Backdrop(props) {
    return <div className={styles.backdrop} onClick={props.onClick}/>;
}


function ModalOverlay(props) {

    const {state} = useContext(AppContext);
    const {selectedUserStory} = state;

    const {
        projectCode,
        userStoryCode,
        description,
        status,
        userStoryEffort
    } = selectedUserStory;

    return (

        <Card className={styles.modal}>
            <div className={styles.close}>
                <BsXCircleFill size={35} onClick={props.onClick}>Close</BsXCircleFill>
            </div>
            <div className={styles.content}>
                <h2 className={styles.modalTitle}><BsInfoCircleFill className={styles.icon}/>{userStoryCode}</h2>
                <div className={styles.body}>
                    <table>
                        <tr>
                            <td>Project Code:</td>
                            <td>{projectCode}</td>
                        </tr>

                        <tr>
                            <td>User Story Code:</td>
                            <td>{userStoryCode}</td>
                        </tr>

                        <tr>
                            <td>Description:</td>
                            <td>{description}</td>
                        </tr>

                        <tr>
                            <td>User Story Status:</td>
                            <td>{status}</td>
                        </tr>
                        <tr>
                            <td>Estimated Effort:</td>
                            <td>{userStoryEffort}</td>
                        </tr>

                    </table>
                </div>

            </div>
        </Card>

    );
}

function UserStoryDetailsModal(props) {

    if (!props.showModal) {
        return null
    }

    return (
        <Fragment>
            {ReactDOM.createPortal(
                <Backdrop onClick={props.onClose}/>,
                document.getElementById("backdrop-root")
            )}
            {ReactDOM.createPortal(
                <ModalOverlay onClick={props.onClose}/>,
                document.getElementById("userStoryDetailsModal-root")
            )}
        </Fragment>
    );
}

export default UserStoryDetailsModal;