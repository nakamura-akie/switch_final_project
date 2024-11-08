import {useContext} from "react";
import AppContext from "../../context/AppContext";

const SprintTableHeader = () => {

    const {state} = useContext(AppContext);
    const {sprintListHeader} = state;

    return (
        <thead>
        <tr>
            <th>{sprintListHeader.header1}</th>
            <th>{sprintListHeader.header2}</th>
            <th>{sprintListHeader.header3}</th>
            <th>{sprintListHeader.header4}</th>
            <th>{sprintListHeader.header5}</th>
        </tr>
        </thead>
    );
}

export default SprintTableHeader;