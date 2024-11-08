import React, { useContext, useEffect } from 'react';
import AppContext from "../../../context/AppContext";
import ResourceTableHeader from "../resourcesTable/ResourceTableHeader"
import ResourceTableBody from "../resourcesTable/ResourceTableBody"
import {fetchResources} from "../../../context/Actions";
import styles from "./ResourcesTable.module.css"

const ResourceTable = () => {
    const { state, dispatch} = useContext(AppContext);
    const {selectedProject} = state
    const {projectCode} = selectedProject;

    const today = new Date();
    const formattedDate = today.toISOString().slice(0,10);

    //const date = "2023-06-01";

    useEffect(() => {
        fetchResources(dispatch, projectCode, formattedDate)
    }, [state.applicationState.resourceSuccess]);

    return (

        <table className={styles.resourcesTable}>
            <ResourceTableHeader/>
            <ResourceTableBody />
        </table>
    );
};

export default ResourceTable;
