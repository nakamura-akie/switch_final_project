import Button from "../components/Button";
import buttonStyles from "../components/Button.module.css";
import AppContext from "../context/AppContext";
import React, {Fragment, useContext} from "react";
import {Outlet, useLocation, useNavigate} from "react-router-dom";
import Header from "../components/Header";
import ResourceSuccessMessageModal from "../components/resource/ResourceSuccessMessageModal";
import ResourceFailureMessageModal from "../components/resource/ResourceFailureMessageModal";
import ResourceTable from "../components/resource/resourcesTable/ResourceTable";

const ResourcesPage = () => {
    const {state} = useContext(AppContext);
    const {selectedProject} = state
    const {projectCode} = selectedProject;
    const {projectName} = selectedProject;
    const navigate = useNavigate();
    const location = useLocation();

    return (
        <div className="resourcesPage">
            {location.pathname === "/projects/" + projectCode + "/resources" ?
                <Fragment>
                    <Header text={projectName + " - Resources"}/>
                    <Button style={buttonStyles.button}
                            onClick={() => navigate("/projects/" + projectCode + "/resources/resourceForm")}>Add
                        Resource</Button>
                    <ResourceTable />
                </Fragment>
                : <Outlet/>}

            {state.applicationState.resourceSuccess && <ResourceSuccessMessageModal/>}
            {state.applicationState.resourceFailure && <ResourceFailureMessageModal/>}

        </div>
    )
}

export default ResourcesPage;