import {useContext} from "react";
import AppContext from "../../../context/AppContext";

const SprintBacklogHeader = () => {

    const {state} = useContext(AppContext);
    const {productBacklogHeaders} = state;

    return (
        <thead>
        <tr>
            <th>{productBacklogHeaders.header1}</th>
            <th>{productBacklogHeaders.header3}</th>
            <th>{productBacklogHeaders.header4}</th>
        </tr>
        </thead>
    );
}

export default SprintBacklogHeader;