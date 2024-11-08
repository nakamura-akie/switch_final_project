import AppContext from "../../context/AppContext";
import React, {useContext} from "react";
import Button from "../Button";
import buttonStyles from "../Button.module.css";
import {getSprintBacklog} from "../../context/Actions";
import styles from "./SprintTable.module.css"

const SprintTableBody = ({filteredSprints, handleClick}) => {

    const {state, dispatch} = useContext(AppContext);
    const {selectedProject} = state;
    const {projectCode} = selectedProject;

    const rows = filteredSprints?.map((row, index) => (
        <tr key={index}>
            <td className="projectCodeRow">{row.sprintCode}</td>
            <td>{row.startDate}</td>
            <td>{row.endDate}</td>
            <td>{row.sprintStatus}</td>
            <td>
                {row.sprintStatus !== "PLANNED" && (

                    <Button
                        style={buttonStyles.button}
                        onClick={() => {
                            handleClick(row);
                            getSprintBacklog(dispatch, projectCode, row.sprintCode);
                        }}
                    >
                        View Backlog
                    </Button>
                )}
            </td>

        </tr>
    ));

    return (
        <tbody className="sprintTableBody">
        {rows}
        </tbody>
    );
};

export default SprintTableBody;