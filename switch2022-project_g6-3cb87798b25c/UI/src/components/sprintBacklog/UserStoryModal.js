import React, {useContext, useEffect, useState} from "react";
import Button from "../Button";
import buttonStyles from "../Button.module.css";
import SearchBox from "./SearchBox";
import {
    fetchUserStories,
    getOpenSprint,
    getSprintBacklog,
    updateSprintBacklogAction
} from "../../context/Actions";
import AppContext from "../../context/AppContext";
import {BsFillCheckCircleFill} from "react-icons/bs";
import formatStyles from "./UserStoryModal.module.css";
import modalStyles from "../../modalStyles/SuccessfulModalStyle.module.css";


const UserStoryModal = () => {

    const {dispatch, state} = useContext(AppContext);
    const [modal, setModal] = useState(false);
    const {selectedProject, openSprint} = state;
    const {projectCode} = selectedProject;
    const {sprintCode} = openSprint;
    const [successModal, setSuccessModal] = useState(false);

    useEffect(() => {
        fetchUserStories(dispatch, projectCode)
    }, []);

    useEffect(() => {
        getOpenSprint(dispatch, projectCode)
    }, []);

    useEffect(() => {
        if (modal || successModal) {
            document.body.style.overflow = "hidden";
        } else {
            document.body.style.overflow = "auto";
        }
    }, [modal, successModal]);

    const toggleModal = () => {
        setModal(!modal);
    };

    const closeMessageModal = () => {
        setSuccessModal(false);
        getSprintBacklog(dispatch, projectCode, sprintCode)
    }

    const handleSubmit = (code) => {
        const body = JSON.stringify({
            userStoryCode: code
        });

        updateSprintBacklogAction(dispatch, projectCode, sprintCode, body);
        setModal(false);

        setTimeout(() => {
            setSuccessModal(true);
        }, 100);
    };

    return (
        <>
            <Button style={buttonStyles.button} onClick={toggleModal}>
                Add User Story
            </Button>

            {modal && (
                <div>
                    <div className={formatStyles.overlay}></div>
                    <div className={formatStyles.modalContent}>
                        <h2>Select User Story</h2>
                        <SearchBox
                            onResultSelection={handleSubmit}
                            projectCode={projectCode}
                            sprintCode={sprintCode}
                        />
                        <div className={formatStyles.button_container}>
                            <Button style={buttonStyles.cancel_button} onClick={toggleModal}>
                                Cancel
                            </Button>
                        </div>
                    </div>
                </div>
            )}

            {successModal && (
                <div>
                    <div className={formatStyles.overlay} onClick={closeMessageModal}></div>
                    <div className={formatStyles.modalContent}>
                        <p><BsFillCheckCircleFill className={modalStyles.icon}/>
                            {state.applicationState.sprintBacklogMessage}</p>

                        <Button style={buttonStyles.confirm_button} onClick={closeMessageModal}>
                            Ok
                        </Button>
                    </div>
                </div>
            )}
        </>
    );
}

export default UserStoryModal;