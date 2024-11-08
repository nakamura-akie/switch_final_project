import React, {Fragment, useContext, useState} from "react";
import AppContext from "../context/AppContext";
import ProjectPageTable from "../components/ProjectPageTable";
import ProductBacklogTable from "../components/productBacklog/ProductBacklogTable";
import SprintPage from "../components/sprint/SprintPage";
import Button from "../components/Button";
import buttonStyles from "../components/Button.module.css";
import {Outlet, useLocation, useNavigate} from "react-router-dom";
import projectPageStyles from "./ProjectPage.module.css";
import {BsFillArrowLeftCircleFill} from "react-icons/bs";
import StatusHistoryModal from "../components/project/StatusHistoryModal";
import {getOpenSprint, getSprintsByProjectCode} from "../context/Actions";

const ProjectPage = () => {

    const {state, dispatch} = useContext(AppContext);
    const {selectedProject, sprintList} = state
    const {projectName} = selectedProject;
    const {projectCode} = selectedProject;
    const [showStatusHistory, setShowStatusHistory] = useState(false);
    const navigate = useNavigate();
    const location = useLocation();

    const showBacklogHandler = (event) => {
        event.preventDefault();
        navigate("/projects/" + projectCode + "/productBacklog");
    };


    function showSprintTableHandler(event) {
        event.preventDefault();
        navigate("/projects/" + projectCode + "/sprints");
    }

    function showStatusHistoryHandler(event) {
        event.preventDefault();
        setShowStatusHistory(true);
    }

    function onClose() {
        setShowStatusHistory(false);
    }

    return (
        <div className="projectPage">
            {showStatusHistory && <StatusHistoryModal project={selectedProject} onClose={onClose}/>}
            <div className={buttonStyles.buttonBack}>
                <BsFillArrowLeftCircleFill size="50"
                                           onClick={() => navigate(-1)}>Back</BsFillArrowLeftCircleFill>
            </div>
            {location.pathname === "/projects/" + projectCode ?
                <Fragment>
                    <h1>{projectName}</h1>
                    <p className={projectPageStyles.p}>
                        <Button style={buttonStyles.button} onClick={showBacklogHandler}>Product Backlog</Button>
                        <Button style={buttonStyles.button} onClick={showSprintTableHandler}>Sprints</Button>
                        <Button style={buttonStyles.button}
                                onClick={() => navigate("/projects/" + projectCode + "/resources")}>Resources</Button>
                        <Button style={buttonStyles.button} onClick={showStatusHistoryHandler}>Status History</Button>
                    </p>
                    <div className={projectPageStyles.projectDetails}>
                        <ProjectPageTable/>
                    </div>
                </Fragment> : <Outlet/>}
        </div>
    )
}
export default ProjectPage;