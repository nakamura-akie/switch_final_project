import React, {Fragment, useContext} from "react";
import ReactDOM from "react-dom";
import Card from "../project/Card";
import styles from "../../modalStyles/ConfirmModal.module.css";
import buttonStyles from '../Button.module.css';
import Button from "../Button";
import {useNavigate} from "react-router-dom";
import AppContext from "../../context/AppContext";
import {BsInfoCircleFill} from "react-icons/bs";

function Backdrop(props) {
    return <div className={styles.backdrop} onClick={props.onCancel}/>;
}

function calculateHours(percentage) {
    const totalHours = 8;
    const hours = (percentage / 100) * totalHours;
    const formattedHours = Math.floor(hours).toString().padStart(2, '0');
    const minutes = Math.round((hours - formattedHours) * 60).toString().padStart(2, '0');
    return `${formattedHours}:${minutes}`;
}

const formatRole = (roleName) => {
    if (typeof roleName !== 'string') {
        return '';
    }
    const words = roleName.split('_');
    const formattedWords = words.map((word) => {
        const lowerCasedWord = word.toLowerCase();
        return lowerCasedWord.charAt(0).toUpperCase() + lowerCasedWord.slice(1);
    });
    return formattedWords.join(' ');
}


function ModalOverlay(props) {
    const {state} = useContext(AppContext);
    const {selectedProject} = state
    const {projectCode} = selectedProject;
    const navigate = useNavigate();



    const handleSaveClick = () => {
        props.onConfirm();
        navigate("/projects/" + projectCode + "/resources");
    }

    return (
        <Card className={styles.modal}>

            <div className={styles.content}>
                <h2 className={styles.resourceTitle}><BsInfoCircleFill className={styles.icon}/>{props.resource.projectCode}: {state.selectedProject.projectName}</h2>
                <table className="projectPageTable">
                    <tbody>
                    <tr>
                        <td>Email:</td>
                        <td>{props.resource.email}</td>
                    </tr>
                    <tr>
                        <td>Project Code:</td>
                        <td>{props.resource.projectCode}</td>
                    </tr>

                    <tr>
                        <td>Project Role:</td>
                        <td>{formatRole(props.resource.projectRole)}</td>
                    </tr>
                    <tr>
                        <td>Start Date:</td>
                        <td>{props.resource.startDate}</td>
                    </tr>
                    <tr>
                        <td>End Date:</td>
                        <td>{props.resource.endDate}</td>
                    </tr>
                    <tr>
                        <td>Percentage of Allocation:</td>
                        <td>{props.resource.percentageOfAllocation} ({calculateHours(props.resource.percentageOfAllocation)} hours/day)</td>
                    </tr>
                    <tr>
                        <td>Cost Per Hour:</td>
                        <td>{props.resource.costPerHour} €/h</td>
                    </tr>
                    </tbody>
                </table>
            </div>

            <footer className={styles.actions}>
                <Button style={buttonStyles.cancel_button} onClick={props.onCancel}>Cancel</Button>
                <Button style={buttonStyles.confirm_button} onClick={handleSaveClick}>Save</Button>
            </footer>
        </Card>
    );
}

function ConfirmResourceModal(props) {
    return (
        <Fragment>
            {ReactDOM.createPortal(
                <Backdrop title={props.title} resource={props.newResource} onConfirm={props.onConfirm}
                          onCancel={props.onCancel}/>,
                document.getElementById("backdrop-root")
            )}
            {ReactDOM.createPortal(
                <ModalOverlay labels={props.labels} resource={props.newResource} onConfirm={props.onConfirm}
                              onCancel={props.onCancel}/>,
                document.getElementById("confirmResourceModal-root")
            )}
        </Fragment>
    );
}

export default ConfirmResourceModal;
