import React, {Fragment, useContext} from "react";
import ReactDOM from "react-dom";
import Card from "./Card";
import styles from "../../modalStyles/ConfirmModal.module.css";
import buttonStyles from '../Button.module.css';
import Button from "../Button";
import {useNavigate} from "react-router-dom";
import {BsFillCheckCircleFill, BsInfoCircleFill} from "react-icons/bs";
import AppContext from "../../context/AppContext";

function Backdrop(props) {
    return <div className={styles.backdrop} onClick={props.onCancel}/>;
}

function ModalOverlay(props) {
    const {state} = useContext(AppContext);
    const {customers} = state;
    const navigate = useNavigate();

    const handleSaveClick = () => {
        props.onConfirm();
        navigate(-1);
    }

    return (
        <Card className={styles.modal}>
            <div className={styles.content}>
                <div>
                    <h2 className={styles.projectTitle}><BsInfoCircleFill
                        className={styles.icon}/>{props.project.projectName}</h2>
                </div>
                <ul className={styles.projectInfo}>
                    {Object.values(props.project).map((value, index) => {
                        if (value != null) {
                            return (
                                <div key={index}>
                                    {props.labels[index]}: {props.labels[index] === "Customer" ?
                                    customers.find(customer => customer.tin === props.project.customerID).name :
                                    value}
                                </div>
                            );
                        }
                    })}
                </ul>
            </div>

            <footer className={styles.actions}>
                <Button style={buttonStyles.cancel_button} onClick={props.onCancel}>Cancel</Button>
                <Button style={buttonStyles.confirm_button} onClick={handleSaveClick}>Save</Button>
            </footer>
        </Card>
    );
}

function ConfirmProjectModal(props) {
    return (
        <Fragment>
            {ReactDOM.createPortal(
                <Backdrop title={props.title} project={props.newProject} onConfirm={props.onConfirm}
                          onCancel={props.onCancel}/>,
                document.getElementById("backdrop-root")
            )}
            {ReactDOM.createPortal(
                <ModalOverlay labels={props.labels} project={props.newProject} onConfirm={props.onConfirm}
                              onCancel={props.onCancel}/>,
                document.getElementById("confirmProjectModal-root")
            )}
        </Fragment>
    );
}

export default ConfirmProjectModal;
