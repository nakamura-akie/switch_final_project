import React, { useContext, useEffect } from 'react';
import ProjectsListTableHeader from "./ProjectsListTableHeader";
import ProjectsListTableBody from "./ProjectsListTableBody";
import {fetchProjects} from "../../context/Actions";
import AppContext from "../../context/AppContext";
import styles from "./ProjectListTable.module.css"

const Table = (props) => {
    const { state, dispatch} = useContext(AppContext);

    const filteredProjects = state.projectList?._embedded?.projectDTOList?.filter((project) => {
        if (props.filterStatus === "All") {
            return true;
        } else {
            return project.projectStatus === props.filterStatus;
        }
    });
    useEffect(() => {
        fetchProjects(dispatch)
    }, [state.applicationState.projectSuccess]);

    return (
        <table className={styles.projectsTable}>
            <ProjectsListTableHeader/>
            <ProjectsListTableBody filteredProjects={filteredProjects}/>
        </table>
    );
};

export default Table;
