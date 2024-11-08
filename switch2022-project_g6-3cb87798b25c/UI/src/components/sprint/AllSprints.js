import React, {Fragment, useContext, useEffect, useState} from 'react';
import styles from "./SprintTable.module.css"
import AppContext from "../../context/AppContext";
import SprintTableHeader from "./SprintTableHeader";
import SprintTableBody from "./SprintTableBody";
import {getSprintsByProjectCode, selectSprint} from "../../context/Actions";
import {Outlet, useLocation, useNavigate} from "react-router-dom";


const AllSprints = () => {
    const {state, dispatch} = useContext(AppContext);
    const {selectedProject, sprintList} = state;
    const {projectCode} = selectedProject;
    const [filterStatus, setFilterStatus] = useState('All');
    const location = useLocation();
    const navigate = useNavigate();


    const handleFilterChange = (event) => {
        setFilterStatus(event.target.value);
    };

    const filteredSprints = sprintList?._embedded?.openSprintOutputDTOList?.filter((sprint) => {
        if (filterStatus === 'All') {
            return true;
        } else {
            return sprint.sprintStatus === filterStatus;
        }
    });

    useEffect(() => {
        getSprintsByProjectCode(dispatch, projectCode);
    }, []);

    const handleClickViewBacklog = (sprint) => {
        selectSprint(dispatch, sprint);
        navigate("/projects/" + projectCode + "/sprints/all/" + sprint.sprintCode);
    };


        return (
            <div>
                {location.pathname === "/projects/" + projectCode+ "/sprints" + "/all" ?
                    <Fragment>
                        <h1>{selectedProject.projectName} - All Sprints</h1>
                        <div className="dropdown">
                            <span className="filterLabel">Filter:</span>
                            <select className="dropdown-select" value={filterStatus} onChange={handleFilterChange}>
                                <option value="All">All</option>
                                <option value="PLANNED">Planned</option>
                                <option value="OPENED">Opened</option>
                                <option value="CLOSED">Closed</option>
                            </select>
                        </div>
                        <div>
                            <table className={styles.sprintTable}>
                                <SprintTableHeader/>
                                <SprintTableBody filteredSprints={filteredSprints} handleClick={handleClickViewBacklog}/>
                            </table>
                        </div>
                    </Fragment> : <Outlet/>}
            </div>
        )

}

export default AllSprints;