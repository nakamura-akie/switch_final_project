import React, {useContext, useState} from "react";
import AppContext from "../../context/AppContext";
import {postProjectAction} from "../../context/Actions";
import ErrorProjectModal from "./ErrorProjectModal";
import ConfirmProjectModal from "./ConfirmProjectModal";
import styles from "./ProjectForm.module.css";
import buttonStyles from "../Button.module.css";
import Button from "../Button";
import {useNavigate} from "react-router-dom";
import Header from "../Header";
import {BsFillArrowLeftCircleFill} from "react-icons/bs";

function ProjectForm() {
    const {state, dispatch} = useContext(AppContext);
    const businessSectors = state.businessSectors;
    const projectTypologies = state.projectTypologies;
    const customers = state.customers;
    const initialForm = {
        projectCode: null,
        projectName: null,
        description: null,
        customerID: null,
        businessSectorName: null,
        projectTypologyName: null,
        budget: null,
        sprintDuration: null,
        numberPlannedSprints: null,
        startDate: null,
        endDate: null,
    }
    const [error, setError] = useState(null);
    const [projectToSave, setProjectToSave] = useState(null);
    const [formData, setFormData] = useState(initialForm);
    const [formLabels, setFormLabels] = useState([]);
    const navigate = useNavigate();

    const changeHandler = (event) => {
        event.preventDefault();

        setFormData((prevFormData) => ({
            ...prevFormData,
            [event.target.name]: event.target.value
        }))
    }

    const submitHandler = (event) => {
        event.preventDefault();

        // How to move this into a separate method?
        if (formData.startDate > formData.endDate && formData.endDate !== "") {
            setError(() => ({
                    title: 'Invalid input',
                    message: 'End date must be after Start date'
                }
            ));
            return;
        }

        const labels = Array.from(document.querySelectorAll('form label')).map(label => {
            const htmlFor = label.getAttribute('for');
            return htmlFor;

        });

        setFormLabels(labels);
        setProjectToSave(formData);
    }

    const resetHandler = () => {
        setFormData(initialForm)
    }

    const errorHandler = () => {
        setError(null);
    }

    const confirmHandler = () => {

        const projectToSave = {
            projectCode: formData.projectCode,
            projectName: formData.projectName,
            description: formData.description,
            customerID: formData.customerID,
            businessSectorName: formData.businessSectorName,
            projectTypologyName: formData.projectTypologyName,
            sprintDuration: formData.sprintDuration,
            numberPlannedSprints: formData.numberPlannedSprints,
            budget: formData.budget,
            startDate: formData.startDate,
            endDate: formData.endDate
        };

        postProjectAction(dispatch, projectToSave);
        resetHandler();
    };

    const onCancelSaveHandler = () => {
        setProjectToSave(null);
    }

    return (
        <div>
            <div className={buttonStyles.buttonBack}>
                <BsFillArrowLeftCircleFill size="50" onClick={() => navigate(-1)}>Back</BsFillArrowLeftCircleFill>
            </div>
            {error && (
                <ErrorProjectModal
                    title={error.title}
                    message={error.message}
                    onConfirm={errorHandler}
                />
            )}
            {projectToSave && (
                <ConfirmProjectModal
                    labels={formLabels}
                    newProject={projectToSave}
                    onConfirm={confirmHandler}
                    onCancel={onCancelSaveHandler}
                />
            )}
            <Header text="Create Project"/>
            <form className={styles.input} id="myForm" onSubmit={submitHandler}>
                <div>
                    <div>
                        <label htmlFor="Project Code">Project Code:
                            <input
                                name="projectCode"
                                type="text"
                                value={formData.projectCode}
                                onChange={changeHandler}
                                required
                            />
                        </label>
                    </div>
                    <div>
                        <label htmlFor="Project Name">Project Name:
                            <input
                                name="projectName"
                                type="text"
                                value={formData.projectName}
                                onChange={changeHandler}
                                required
                            />
                        </label>
                    </div>
                </div>
                <div>
                    <div>
                        <label htmlFor="Project Description">
                            Project Description:
                            <textarea
                                name="description"
                                value={formData.description}
                                onChange={changeHandler}
                                required
                            />
                        </label>
                    </div>
                </div>
                <div>
                    <div>
                        <label htmlFor="Customer">
                            Customer:
                            <select
                                name="customerID"
                                value={formData.customerID}
                                onChange={changeHandler}
                                required
                            >
                                <option default value="">Select a Customer</option>
                                {customers.map((customerID) => (
                                    <option key={customerID.name} value={customerID.tin}>
                                        {customerID.name}
                                    </option>
                                ))}
                            </select>
                        </label>
                    </div>
                    <div>
                        <label htmlFor="Business Sector">
                            Business Sector:
                            <select name="businessSectorName"
                                    value={formData.businessSectorName}
                                    onChange={changeHandler}
                                    required

                            >
                                <option default value="">Select a Business Sector</option>
                                {businessSectors.map((businessSector) => (
                                    <option key={businessSector.name}>
                                        {businessSector.name}
                                    </option>
                                ))}
                            </select>
                        </label>
                    </div>
                </div>

                <div>
                    <div>
                        <label htmlFor="Project Typology">
                            Project Typology:
                            <select name="projectTypologyName"
                                    value={formData.projectTypologyName}
                                    onChange={changeHandler}
                                    required
                            >
                                <option default value="">Select a Project Typology</option>
                                {projectTypologies.map((projectTypology) => (
                                    <option key={projectTypology.name}>
                                        {projectTypology.name}
                                    </option>
                                ))}
                            </select>
                        </label>
                    </div>

                    <div>
                        <label htmlFor="Budget">Budget:
                            <input
                                name="budget"
                                value={formData.budget}
                                onChange={changeHandler}
                                min="0"
                            />
                        </label>
                    </div>
                </div>

                <div>
                    <div>
                        <label htmlFor="Sprint Duration">Sprint duration (weeks):
                            <input
                                name="sprintDuration"
                                type="number"
                                value={formData.sprintDuration}
                                min="1"
                                onChange={changeHandler}
                            />
                        </label>
                    </div>

                    <div>
                        <label htmlFor="Number of Planned Sprints">Number of planned Sprints:
                            <input
                                name="numberPlannedSprints"
                                type="number"
                                value={formData.numberPlannedSprints}
                                onChange={changeHandler}
                                min="1"
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
                                value={formData.startDate}
                                onChange={changeHandler}
                            />
                        </label>
                    </div>

                    <div>
                        <label htmlFor="End date">End date:
                            <input
                                name="endDate"
                                type="date"
                                value={formData.endDate}
                                onChange={changeHandler}
                            />
                        </label>
                    </div>
                </div>
                <div className={styles.button_container}>
                    <Button style={buttonStyles.reset_button} onClick={resetHandler} type="reset">Reset</Button>
                    <Button type="submit" style={buttonStyles.button}>Add project</Button>
                </div>
            </form>
        </div>
    )
}

export default ProjectForm;
