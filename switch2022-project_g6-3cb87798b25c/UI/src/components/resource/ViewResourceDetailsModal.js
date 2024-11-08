import React, {Fragment, useContext} from "react";
import ReactDOM from "react-dom";
import Card from "../project/Card";
import styles from "../../modalStyles/DetailsModal.module.css";
import AppContext from "../../context/AppContext";
import {BsInfoCircleFill, BsXCircleFill} from "react-icons/bs";

function Backdrop(props) {
    return <div className={styles.backdrop} onClick={props.onExit}/>;
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
    const {selectedResource} = state;

    const {
        email,
        projectCode,
        startDate,
        endDate,
        projectRole,
        percentageOfAllocation,
        costPerHour,
    } = selectedResource

    return (
        <Card className={styles.modal}>
            <div className={styles.close}>
                <BsXCircleFill size={35} onClick={props.onExit}>Close</BsXCircleFill>
            </div>
            <div className={styles.resourceContent}>
                <h2 className={styles.resourceTitle}><BsInfoCircleFill className={styles.icon}/>{email}</h2>
                <table className="projectPageTable">
                    <tbody>
                    <tr>
                        <td>Email:</td>
                        <td>{email}</td>
                    </tr>
                    <tr>
                        <td>Project Code:</td>
                        <td>{projectCode}</td>
                    </tr>

                    <tr>
                        <td>Project Role:</td>
                        <td>{formatRole(projectRole)}</td>
                    </tr>
                    <tr>
                        <td>Start Date:</td>
                        <td>{startDate}</td>
                    </tr>
                    <tr>
                        <td>End Date:</td>
                        <td>{endDate}</td>
                    </tr>
                    <tr>
                        <td>Percentage of Allocation:</td>
                        <td>{percentageOfAllocation} ({calculateHours(percentageOfAllocation)} hours/day)</td>
                    </tr>
                    <tr>
                        <td>Cost Per Hour:</td>
                        <td>{costPerHour} €/h</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </Card>

    );
}

function ViewResourceDetailsModal(props) {

    return (
        <Fragment>
            {ReactDOM.createPortal(
                <Backdrop onExit={props.onExit}/>,
                document.getElementById("backdrop-root")
            )}
            {ReactDOM.createPortal(
                <ModalOverlay onExit={props.onExit}/>,
                document.getElementById("viewResourceDetailsModal-root")
            )}
        </Fragment>
    );
}

export default ViewResourceDetailsModal;
