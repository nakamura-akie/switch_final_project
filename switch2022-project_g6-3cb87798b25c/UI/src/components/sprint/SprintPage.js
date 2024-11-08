import React, {Fragment, useContext, useEffect, useState} from 'react';
import "../../index.css"
import Button from "../Button";
import buttonStyles from "../Button.module.css";
import AppContext from "../../context/AppContext";
import {Outlet, useLocation, useNavigate} from "react-router-dom";
import ScrumBoard from "../sprintBacklog/ScrumBoard";
import {
    getOpenSprint,
    getSprintsByProjectCode,
    updateSprintStatusAction
} from "../../context/Actions";
import CloseSprintConfirmationModal from "./CloseSprintConfirmationModal";
import styles from "./SprintPage.module.css"


const SprintPage = () => {

    const {state, dispatch} = useContext(AppContext);
    const {selectedProject, openSprint, sprintList} = state;
    const {projectCode} = selectedProject;


    const [showSprintCloseModal, setShowSprintCloseModal] = useState(false);
    const [showTable, setShowTable] = useState(true);


    const navigate = useNavigate();
    const location = useLocation();


    useEffect(() => {
        getOpenSprint(dispatch, projectCode)
    }, []);

    useEffect(() => {
        getSprintsByProjectCode(dispatch, projectCode)
    }, []);


    const handleClick = () => {
        navigate("/projects/" + projectCode + "/sprints/sprintForm");
    };

    const handleClickAllSprints = () => {
        navigate("/projects/" + projectCode + "/sprints/all");
    };

    const handleCancelCloseSprint = () => {
        setShowSprintCloseModal(false);
        navigate("/projects/" + projectCode + "/sprints")
    };

    const handleModalCloseSprint = () => {
        setShowSprintCloseModal(true);
    };

    const handleClickUpDateSprintStatus = () => {
        getSprintsByProjectCode(dispatch, projectCode);

        const body = JSON.stringify({
            "sprintID": {
                "projectCode": projectCode,
                "sprintCode": openSprint.sprintCode
            },
            "sprintStatus": "CLOSED"
        });

        updateSprintStatusAction(dispatch, body);

        setShowSprintCloseModal(false);
        setShowTable(false);
        state.openSprint = {
            sprintCode: '',
            startDate: '',
            endDate: '',
            sprintStatus: ''
        }
    };


    const handleOpenNextSprint = () => {
        getSprintsByProjectCode(dispatch, projectCode)
        const nextSprints = sprintList?._embedded?.openSprintOutputDTOList?.filter((sprint) => {
            return sprint.sprintStatus === 'PLANNED';
        });

        const nextSprint = nextSprints[0];

        const body = JSON.stringify({
            "sprintID": {
                "projectCode": projectCode,
                "sprintCode": nextSprint.sprintCode
            },
            "sprintStatus": "OPENED"
        });
        updateSprintStatusAction(dispatch, body);
        setShowTable(true);

        setTimeout(() => {
            getOpenSprint(dispatch, projectCode);
        }, 50);

    }

    const filteredList = sprintList?._embedded?.openSprintOutputDTOList?.filter((sprint) => sprint.sprintStatus === 'PLANNED');

    return (

        <div>
            {location.pathname === "/projects/" + projectCode + "/sprints" ?
                <Fragment>
                    <div>
                        <h1> {selectedProject.projectName} - Sprints</h1>
                        <Button style={buttonStyles.button} onClick={handleClick}>Create Sprint</Button>
                        <Button style={buttonStyles.button} onClick={handleClickAllSprints}>All Sprints</Button>
                    </div>

                    {(showTable && openSprint.sprintStatus === "OPENED") ? (
                        <>
                            <div>
                                <h2>Currently Open Sprint</h2>
                                <table className={styles.openSprintTable}>
                                    <theader>
                                        <td>{openSprint.sprintCode}</td>
                                        <td><b>Start Date:</b> {openSprint.startDate}</td>
                                        <td><b>End Date:</b> {openSprint.endDate}</td>
                                        <td><b>Status:</b> {openSprint.sprintStatus}</td>
                                            <Button
                                                style={buttonStyles.button}
                                                onClick={handleModalCloseSprint}>
                                                Close Sprint
                                            </Button>
                                            <CloseSprintConfirmationModal
                                                showModal={showSprintCloseModal}
                                                onConfirm={handleClickUpDateSprintStatus}
                                                onClose={handleCancelCloseSprint}
                                            />
                                    </theader>
                                </table>

                                <ScrumBoard/>

                            </div>
                        </>
                    ) : (filteredList && filteredList.length > 0 && (
                            <div>
                                <Button style={buttonStyles.button} onClick={handleOpenNextSprint}>
                                    Open next Sprint
                                </Button>
                            </div>
                        )
                    )}
                </Fragment> : <Outlet/>}

        </div>
    );
}

export default SprintPage;