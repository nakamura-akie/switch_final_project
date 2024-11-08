import React, {useContext, useEffect} from "react";
import AppContext from "../../../context/AppContext";
import {getSprintBacklog} from "../../../context/Actions";

const SprintBacklogBody = () => {
    const {state, dispatch} = useContext(AppContext);
    const {selectedProject, selectedSprint, sprintBacklog} = state;

    useEffect(() => {
        getSprintBacklog(dispatch, selectedProject.projectCode, selectedSprint.sprintCode)
    }, []);

    const rows = sprintBacklog?._embedded?.userStoryDTOList?.map((row, index) =>
        <tr key={index}>
            <td>{row.userStoryCode.userStoryCodeValue}</td>
            <td>{row.description.descriptionValue}</td>
            <td>{row.status.toString()}</td>
        </tr>
    );

    return (
        <tbody className="productBacklogBody">
        {rows}
        </tbody>
    );
};

export default SprintBacklogBody;