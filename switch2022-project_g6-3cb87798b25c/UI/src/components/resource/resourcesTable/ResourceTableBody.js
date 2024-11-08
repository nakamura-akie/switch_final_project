import AppContext from "../../../context/AppContext";
import React, {Fragment, useContext, useEffect, useState} from "react";
import Button from "./../../Button";
import buttonStyles from "./../../Button.module.css"
import {selectResource, getResourceByResourceID} from "../../../context/Actions";
import ViewResourceDetailsModal from "../ViewResourceDetailsModal";

const ResourceTableBody = () => {
    const {state, dispatch} = useContext(AppContext);
    const {resourcesList} = state;
    const [showResourceDetails, setShowResourceDetails] = useState(false);

    const handleViewDetailsClick = (resource) => {
        selectResource(dispatch, resource)
        setShowResourceDetails(true);
    }

    const onExitHandler = () => {
        setShowResourceDetails(false);
    }
    
    const formatRole = (roleName) => {
        const words = roleName.split('_');
        const formattedWords = words.map((word) => {
            const lowerCasedWord = word.toLowerCase();
            return lowerCasedWord.charAt(0).toUpperCase() + lowerCasedWord.slice(1);
        });
        return formattedWords.join(' ');
    }

    const rows = resourcesList?._embedded?.resourceDTOList?.map((row, index) =>
        <tr key={index}>
            <td>{row.email}</td>

            <td>{formatRole(row.projectRole)}</td>
            <td>{row.startDate}</td>
            <td>{row.endDate}</td>
            <td>
                <Button style={buttonStyles.button} onClick={() => {
                    handleViewDetailsClick(row);
                    getResourceByResourceID(dispatch, row.email, row.projectCode, row.startDate, row.endDate);
                }}> View details</Button>
            </td>
        </tr>
    );

    return (
        <Fragment>
            <tbody className="projectsTableBody">
            {rows}
            </tbody>
            {showResourceDetails && <ViewResourceDetailsModal onExit={onExitHandler}/>}
        </Fragment>

    );
};

export default ResourceTableBody;
