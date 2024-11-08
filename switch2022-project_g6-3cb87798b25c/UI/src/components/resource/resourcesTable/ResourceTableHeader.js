import {useContext} from "react";
import AppContext from "../../../context/AppContext";

const ResourceTableHeader = () => {

    const {state} = useContext(AppContext);
    const {resourceTableHeaders} = state;

    return (
        <thead>
        <tr>
            <th>{resourceTableHeaders.header1}</th>
            <th>{resourceTableHeaders.header3}</th>
            <th>{resourceTableHeaders.header4}</th>
            <th>{resourceTableHeaders.header5}</th>
            <th></th>
        </tr>
        </thead>
    );
}

export default ResourceTableHeader;