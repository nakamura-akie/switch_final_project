import React, {useContext, useEffect, useState} from 'react';
import AppContext from '../../context/AppContext';
import {postUserStoryAction} from '../../context/Actions';
import Button from "../Button";
import buttonStyles from "../Button.module.css";
import styles from "./UserStoryForm.module.css";
import {BsFillArrowLeftCircleFill} from "react-icons/bs";
import UserStoryConfirmationModal from "./UserStoryConfirmationModal";
import FailureUserStoryModal from "./FailureUserStoryModal";
import UserStorySuccessModel from "./UserStorySuccessModal";

function UserStoryForm() {
    const {state, dispatch} = useContext(AppContext);
    const {selectedProject} = state
    const {projectCode} = selectedProject;
    const [userStoryToSave, setUserStory] = useState(null);
    const initialForm = {
        projectCode: selectedProject.projectCode,
        userStoryCode: "",
        userStoryDescription: "",
        status: "PLANNED"
    }
    const [userStoryState] = useState('PLANNED');
    const [usFormLabels, usSetFormLabels] = useState([]);


    const [userStoryFormData, setUserStoryFormData] = useState(initialForm)

    const changeHandler = (event) => {
        event.preventDefault();

        setUserStoryFormData((prevUserStoryFormData) => ({
            ...prevUserStoryFormData,
            [event.target.name]: event.target.value
        }))
    }


    const handleSubmit = (event) => {
        event.preventDefault();

        const labels = Array.from(document.querySelectorAll('form label')).map(label => {
            const htmlFor = label.getAttribute('for');
            return htmlFor;
        });

        usSetFormLabels(labels);
        setUserStory(userStoryFormData);
    };

    const confirmHandler = () => {

        const body = JSON.stringify({
            "projectCode": userStoryFormData.projectCode,
            "userStoryCode": userStoryFormData.userStoryCode,
            "description": userStoryFormData.userStoryDescription,
            "status": userStoryState
        });

        postUserStoryAction(dispatch, body);
        resetHandler();
    };

    const resetHandler = () => {
        setUserStoryFormData(initialForm);
        setUserStory(null);
    }

    const onCancelSaveHandler = () => {
        setUserStory(null);
    }

        return (
            <>
                <div>
                    {userStoryToSave && (
                        <UserStoryConfirmationModal
                            labels={usFormLabels}
                            newUserStory={userStoryToSave}
                            onConfirm={confirmHandler}
                            onCancel={onCancelSaveHandler}/>
                    )}

                    {state.applicationState.userStoryFailure && <FailureUserStoryModal/>}
                    {state.applicationState.userStorySuccess && <UserStorySuccessModel/>}

                    <h1>{selectedProject.projectName} - Create User Story</h1>
                    <form className={styles.input} onSubmit={handleSubmit} onReset={resetHandler}>

                        <label htmlFor="Project Code" className={styles.inputLabel}>Project</label>
                            <textarea
                                name="projectCode"
                                className={styles.textarea_size_code}
                                disabled
                                value={projectCode}
                            />


                        <label htmlFor="User Story Code" className={styles.inputLabel}>User Story Code</label>
                            <textarea
                                name="userStoryCode"
                                className={styles.textarea_size_code}
                                required
                                value={userStoryFormData.userStoryCode}
                                onChange={changeHandler}
                            />


                        <label htmlFor="User Story Description" className={styles.inputLabel}>Description</label>
                            <textarea
                                name="userStoryDescription"
                                className={styles.textarea_size_description}
                                required
                                value={userStoryFormData.userStoryDescription}
                                onChange={changeHandler}
                            />



                        <div className={styles.button_container}>
                            <Button style={buttonStyles.reset_button}
                                    type='reset'>Reset</Button>
                            <Button style={buttonStyles.button}
                                    type='submit'>Add to Product Backlog</Button>
                        </div>
                    </form>
                </div>
            </>
        );
}

export default UserStoryForm;