import buttonStyles from "../Button.module.css";
import Button from "../Button";
import AppContext from "../../context/AppContext";
import {useNavigate} from "react-router-dom";
import React, {Fragment, useContext, useState} from "react";
import styles from "./ResourceForm.module.css";
import ErrorResourceModal from "./ErrorResourceModal";
import ConfirmResourceModal from "./ConfirmResourceModal";
import {postResourceAction} from "../../context/Actions";

function ResourceForm() {
    const {state, dispatch} = useContext(AppContext);
    const {selectedProject} = state;
    const {projectCode} = selectedProject;
    const navigate = useNavigate();
    const accountsList = state.accountsList;
    const roleList = state.roleList;

    const initialResourceForm = {
        email: "",
        projectCode: selectedProject.projectCode,
        startDate: "",
        endDate: "",
        projectRole: "",
        percentageOfAllocation: 50,
        costPerHour: "",
    }

    const [error, setError] = useState(null);
    const [resourceFormData, setResourceFormData] = useState(initialResourceForm);
    const [resourceFormLabels, setResourceFormLabels] = useState([]);
    const [resourceToSave, setResourceToSave] = useState(null);

    const changeHandler = (event) => {
        event.preventDefault();

        setResourceFormData((prevResourceFormData) => ({
            ...prevResourceFormData,
            [event.target.name]: event.target.value
        }))
    }

    const calculateEquivalentHours = (percentage) => {
        const totalMinutes = (percentage / 100) * 8 * 60; // Convert hours to minutes
        const hours = Math.floor(totalMinutes / 60);
        const minutes = Math.round(totalMinutes % 60);

        return `${hours.toString().padStart(2, '0')}h${minutes.toString().padStart(2, '0')}min`;
    };

    const submitHandler = (event) => {
        event.preventDefault();

        if (resourceFormData.startDate > resourceFormData.endDate && resourceFormData.endDate !== "") {
            setError(() => ({
                    title: 'Invalid input',
                    message: 'End date must be after Start date'
                }
            ));
            return;
        }

        const labels = Array.from(document.querySelectorAll('form label')).map(label => {
            return label.getAttribute('for');

        });
        setResourceFormLabels(labels);
        setResourceToSave(resourceFormData);
    }

    const resetHandler = () => {
        setResourceFormData(initialResourceForm);
    }

    const errorHandler = () => {
        setError(null);
    }

    const confirmHandler = () => {
        const body = JSON.stringify({
            "email": resourceFormData.email,
            "projectCode": resourceFormData.projectCode,
            "startDate": resourceFormData.startDate,
            "endDate": resourceFormData.endDate,
            "projectRole": resourceFormData.projectRole,
            "percentageOfAllocation": resourceFormData.percentageOfAllocation,
            "costPerHour": resourceFormData.costPerHour
        });

        postResourceAction(dispatch, body);
        resetHandler();
    };

    const onCancelSaveHandler = () => {
        setResourceToSave(null);
    }

    const formatRole = (roleName) => {
        const words = roleName.split('_');
        const formattedWords = words.map((word) => {
            const lowerCasedWord = word.toLowerCase();
            return lowerCasedWord.charAt(0).toUpperCase() + lowerCasedWord.slice(1);
        });
        return formattedWords.join(' ');
    }


    return (
        <Fragment>
            {error && (
                <ErrorResourceModal
                    title={error.title}
                    message={error.message}
                    onConfirm={errorHandler}
                />
            )}
            {resourceToSave && (
                <ConfirmResourceModal
                    labels={resourceFormLabels}
                    newResource={resourceToSave}
                    onConfirm={confirmHandler}
                    onCancel={onCancelSaveHandler}
                />
            )}
            <h1> {selectedProject.projectName} - Add Resource</h1>
            <form className={styles.input} id="myResourceForm" onSubmit={submitHandler}>
                <div>

                </div>
                <div>
                    <div>
                        <label htmlFor="User Account">
                            User Account:
                            <select
                                name="email"
                                value={resourceFormData.email}
                                onChange={changeHandler}
                                required
                            >
                                <option>Select a User Account</option>
                                {accountsList.map((account) => (
                                    <option key={account.email} value={account.email}>
                                        {account.email}
                                    </option>
                                ))}
                            </select>
                        </label>
                    </div>
                    <div>
                        <label htmlFor="Project Code">Project Code:
                            <input
                                name="projectCode"
                                value={projectCode}
                                onChange={changeHandler}
                                disabled
                            />
                        </label>
                    </div>
                </div>
                <div>
                    <div>
                        <label htmlFor="Start Date">Start date:
                            <input
                                name="startDate"
                                type="date"
                                value={resourceFormData.startDate}
                                onChange={changeHandler}
                                required
                            />
                        </label>
                    </div>

                    <div>
                        <label htmlFor="End date">End date:
                            <input
                                name="endDate"
                                type="date"
                                value={resourceFormData.endDate}
                                onChange={changeHandler}
                                required
                            />
                        </label>
                    </div>
                </div>
                <div>
                    <div>
                        <label htmlFor="Role">
                            Role:
                            <select
                                name="projectRole"
                                value={resourceFormData.projectRole}
                                onChange={changeHandler}
                                required
                            >
                                <option>Select a Role</option>
                                {roleList.map((role) => (
                                    <option key={role.name} value={role.name}>
                                        {formatRole(role.name)}
                                    </option>
                                ))}
                            </select>
                        </label>
                    </div>
                    <div>
                        <div style={{position: "relative"}}>
                            <label htmlFor="Daily Time Allocation">
                                Daily Time Allocation: {calculateEquivalentHours(resourceFormData.percentageOfAllocation)}
                                <input
                                    name="percentageOfAllocation"
                                    type="range"
                                    min="0"
                                    max="100"
                                    value={resourceFormData.percentageOfAllocation}
                                    onChange={changeHandler}
                                />
                            </label>
                            <span
                                style={{
                                    position: "absolute",
                                    left: `${(resourceFormData.percentageOfAllocation / 100) * 100}%`,
                                    top: "150%",
                                    transform: "translateX(-50%)",
                                }}
                            > {resourceFormData.percentageOfAllocation}%
                            </span>
                        </div>
                    </div>
                </div>
                <div>
                    <div>
                        <label htmlFor="Cost Per Hour">Cost Per Hour:
                            <input
                                name="costPerHour"
                                min="0"
                                value={resourceFormData.costPerHour}
                                onChange={changeHandler}

                            />
                        </label>
                    </div>
                </div>
                <div className={styles.button_container}>
                    <Button style={buttonStyles.reset_button} onClick={resetHandler} type="reset">Reset</Button>
                    <Button type="submit" style={buttonStyles.button}>Add resource</Button>
                </div>
            </form>
        </Fragment>
    )
}

export default ResourceForm;
