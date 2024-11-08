import React, {useContext, useState} from "react";
import AppContext from "../../context/AppContext";
import {postSprintAction, saveSprint} from "../../context/Actions";
import buttonStyles from "../Button.module.css";
import Button from "../Button";
import styles from "./SprintForm.module.css";
import ConfirmSprintCreationModal from "./ConfirmSprintCreationModal";
import FailureSprintCreationModal from "./FailureSprintCreationModal";
import SuccessSprintCreationModal from "./SuccessSprintCreationModal";

function SprintForm() {
    const {state, dispatch} = useContext(AppContext);
    const {selectedProject} = state;
    const {projectCode} = selectedProject;

    const [sprintStartDate, setSprintStartDate] = useState("");
    const [newSprint, setSprint] = useState(null);
    const [sprintFormLabels, sprintSetFormLabels] = useState([]);
    const initialForm = {
        projectCode: selectedProject.projectCode,
        startDate: ""
    }
    const [sprintFormData, setSprintFormData] = useState(initialForm)


    const today = new Date().toISOString().split("T")[0];

    const confirmHandler = () => {
        saveSprint(dispatch, {startDate: sprintStartDate});

        const body = JSON.stringify({
            "projectCode": projectCode,
            "startDate": sprintFormData.startDate
        });
        postSprintAction(dispatch, body);
        saveSprint(dispatch, {startDate: sprintStartDate});

        resetHandler();
    };

    const resetHandler = () => {
        setSprintFormData(initialForm);
        setSprint(null);
    }

    const onCancelSaveHandler = () => {
        setSprint(null);
    }

    const handleSubmit = (event) => {
        event.preventDefault();

        const labels = Array.from(document.querySelectorAll('form label')).map(label => {
            const htmlFor = label.getAttribute('for');
            return htmlFor;
        });

        sprintSetFormLabels(labels);
        setSprint(sprintFormData);
    };


    const handleSprintStartDateChange = (event) => {
        event.preventDefault();
        const selectedDate = event.target.value;

        setSprintFormData((prevFormData) => ({
            ...prevFormData,
            startDate: selectedDate
        }));

        setSprintStartDate(selectedDate);
    };

    return (
        <div>
            {newSprint && (
                <ConfirmSprintCreationModal
                    labels={sprintFormLabels}
                    newSprint={newSprint}
                    onConfirm={confirmHandler}
                    onCancel={onCancelSaveHandler}/>
            )}

            {state.applicationState.sprintCreationFailure && <FailureSprintCreationModal/>}
            {state.applicationState.sprintCreationSuccess && <SuccessSprintCreationModal/>}

            <h1>{selectedProject.projectName} - Create Sprint</h1>
            <form className={styles.input} onSubmit={handleSubmit}>

                <div className={styles.sprintTableForm}>

                    <label htmlFor="sprintStartDate">
                        Sprint start date:
                        <input
                            className={styles.date}
                            type="date"
                            id="sprintStartDate"
                            name="startDate"
                            min={today}
                            value={sprintStartDate}
                            onChange={handleSprintStartDateChange}
                            required
                        />
                    </label>
                </div>

                <div className={styles.addSprintButton}>
                    <Button style={buttonStyles.button} type="submit">
                        Add Sprint
                    </Button>
                </div>
            </form>
        </div>
    );
}

export default SprintForm;