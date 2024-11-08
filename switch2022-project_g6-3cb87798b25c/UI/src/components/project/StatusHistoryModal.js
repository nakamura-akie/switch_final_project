import React, {Fragment, useContext} from "react";
import ReactDOM from "react-dom";
import Card from "./Card";
import styles from "./StatusHistoryModal.module.css";
import {useNavigate} from "react-router-dom";
import {BsInfoCircleFill, BsXCircleFill} from "react-icons/bs";
import AppContext from "../../context/AppContext";

function Backdrop(props) {
    return <div className={styles.backdrop} onClick={props.onClose}/>;
}

function ModalOverlay(props) {
    const {state} = useContext(AppContext);
    const {customers} = state;

    const navigate = useNavigate();

    const handleSaveClick = () => {
        props.onConfirm();
        navigate(-1);
    }

    const {statusHistory} = props.project;

    return (
        <Card className={styles.modal}>
            <div className={styles.close}>
                <BsXCircleFill size={35} onClick={props.onClose}>Close</BsXCircleFill>
            </div>
            <div className={styles.content}>
                <h2 className={styles.modalTitle}>
                    <BsInfoCircleFill
                        className={styles.icon}/>{"Status History for Project " + props.project.projectCode}
                </h2>
                <div className={styles.body}>
                    <table>
                        <thead>
                            <th>Status</th>
                            <th>Date of change</th>
                        </thead>
                        <tbody>
                        {Object.entries(statusHistory).map((value, index) => {
                            return (
                                <tr key={index} className={styles.rows}>
                                    <td>{value[0]}</td>
                                    <td>{value[1]}</td>
                                </tr>
                            )
                        })}
                        </tbody>
                    </table>
                </div>
            </div>
        </Card>
    );
}

function ConfirmProjectModal(props) {
    return (
        <Fragment>
            {ReactDOM.createPortal(
                <Backdrop onClose={props.onClose}/>,
                document.getElementById("backdrop-root")
            )}
            {ReactDOM.createPortal(
                <ModalOverlay project={props.project} onClose={props.onClose}/>,
                document.getElementById("confirmProjectModal-root")
            )}
        </Fragment>
    );
}

export default ConfirmProjectModal;
