import React, {Fragment, useContext, useState} from "react";
import Header from "../components/Header";
import Table from "../components/projectsList/Table";
import AppContext from "../context/AppContext";
import SuccessMessageModal from '../components/project/SuccessMessageModal';
import FailureMessageModal from "../components/project/FailureMessageModal";
import buttonStyles from "../components/Button.module.css";
import {selectProject} from "../context/Actions";
import {Outlet, useLocation, useNavigate} from "react-router-dom";
import Button from "../components/Button";

const Projects = () => {
    const { state, dispatch } = useContext(AppContext);

    const navigate = useNavigate();
    const location = useLocation();

    const [filterStatus, setFilterStatus] = useState("All");

    const handleFilterChange = (event) => {
        setFilterStatus(event.target.value);
    };


    const handleClick = (project) => {
        selectProject(dispatch, project);
    };

    return (
        <Fragment>
            {location.pathname === "/projects" ? (
                <Fragment>
                    <Header text="Projects" />
                    <div>
                        <Button
                            style={buttonStyles.button}
                            onClick={() => navigate("/projects/projectForm")}
                        >
                            Create a new project
                        </Button>
                    </div>
                    <div className="dropdown">
                        <br/>
                        <span className="filterLabel">Filter:</span>
                        <select className="dropdown-select" value={filterStatus} onChange={handleFilterChange}>
                            <option value="All">All</option>
                            <option value="PLANNED">Planned</option>
                            <option value="INCEPTION">Inception</option>
                            <option value="ELABORATION">Elaboration</option>
                            <option value="CONSTRUCTION">Construction</option>
                            <option value="TRANSITION">Transition</option>
                            <option value="WARRANTY">Warranty</option>
                            <option value="CLOSED">Closed</option>
                        </select>
                    </div>
                    <Table filterStatus={filterStatus}/>
                </Fragment>
            ) : (
                <Outlet />
            )}

            {state.applicationState.projectSuccess && <SuccessMessageModal />}
            {state.applicationState.projectFailure && <FailureMessageModal />}
        </Fragment>
    );
};

export default Projects;
