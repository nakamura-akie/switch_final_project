import {useContext} from "react";
import AppContext from "../../context/AppContext";
import styles from "./ProjectListTable.module.css"

const ProjectsListTableHeader = () => {

    const {state} = useContext(AppContext);
    const {projectListHeaders} = state;

    return (
        <thead>
        <tr className={styles.header}>
            <th>{projectListHeaders.header1}</th>
            <th>{projectListHeaders.header2}</th>
            <th>{projectListHeaders.header3}</th>
            <th>{projectListHeaders.header4}</th>
            <th className={styles.nonExistent}></th>
        </tr>
        </thead>
    );
}

export default ProjectsListTableHeader;