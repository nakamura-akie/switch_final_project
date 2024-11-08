import React, {useContext, useState} from "react";
import AppContext from "../context/AppContext";
import styles from "../pages/ProjectPage.module.css"

const ProjectPageTable = () => {

    const {state} = useContext(AppContext);
    const {customers} = state;
    const {selectedProject} = state;


    const placeHolderForEmptyValue = (value) => {
        if (value === undefined || value === "" || value === null) {
            return "N/A";
        }else{
            return value;
        }
    }

    const {
        projectCode,
        projectName,
        description,
        customerID,
        businessSectorName,
        projectTypologyName,
        status = "N/A",
        sprintDuration = "N/A",
        numberPlannedSprints= "N/A",
        budget = "N/A",
        startDate = "N/A",
        endDate = "N/A"
    } = selectedProject;

    return (
        <table className={styles.projectPageTable}>
            <tr>
                <td>Project Code:</td>
                <td>{projectCode}</td>
            </tr>

            <tr>
                <td>Project Name:</td>
                <td>{projectName}</td>
            </tr>

            <tr>
                <td>Description:</td>
                <td>{placeHolderForEmptyValue(description)}</td>
            </tr>

            <tr>
                <td>Customer:</td>
                <td>{customers.find(customer => customer.tin === customerID).name}</td>
                {/*<td>{placeHolderForEmptyValue(customerID)}</td>*/}
            </tr>

            <tr>
                <td>Business Sector:</td>
                <td>{businessSectorName}</td>
            </tr>

            <tr>
                <td>Project Typology:</td>
                <td>{projectTypologyName}</td>
            </tr>

            <tr>
                <td>Budget:</td>
                <td>{placeHolderForEmptyValue(budget)}</td>
            </tr>

            <tr>
                <td>Sprint Duration:</td>
                <td>{placeHolderForEmptyValue(sprintDuration)}</td>
            </tr>

            <tr>
                <td>Number of planned Sprints:</td>
                <td>{placeHolderForEmptyValue(numberPlannedSprints)}</td>
            </tr>

            <tr>
                <td>Start Date:</td>
                <td>{placeHolderForEmptyValue(startDate)}</td>
            </tr>
            <tr>
                <td>End Date:</td>
                <td>{placeHolderForEmptyValue(endDate)}</td>
            </tr>

            <tr>
                <td>Project Status:</td>
                <td>{status}</td>
            </tr>

        </table>
    );
};

export default ProjectPageTable;
