import React, {useContext} from "react";
import SprintBacklogHeader from "./SprintBacklogHeader";
import SprintBacklogBody from "./SprintBacklogBody";
import styles from "./ClosedSprintBacklogTable.module.css"
import AppContext from "../../../context/AppContext";

const SprintBacklogTable = () => {
    const {state} = useContext(AppContext);
    const {selectedProject, selectedSprint} = state;

    return (

        <div>
            <h1> {selectedProject.projectName} - Sprint {selectedSprint.sprintCode} - Sprint Backlog</h1>
            <table className={styles.sprintTable}>
                <SprintBacklogHeader/>
                <SprintBacklogBody/>
            </table>

        </div>
    )
}
export default SprintBacklogTable;