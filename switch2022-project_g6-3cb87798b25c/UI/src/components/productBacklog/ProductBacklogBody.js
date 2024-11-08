import AppContext from "../../context/AppContext";
import React, {useContext, useEffect} from "react";
import {fetchUserStories} from "../../context/Actions";

const ProductBacklogBody = (props) => {
    const projectCode = props.projectCode;
    const {state, dispatch} = useContext(AppContext);
    const {userStoryList} = state;

    useEffect(() => {
        fetchUserStories(dispatch, projectCode)
    }, []);

    const rows = userStoryList?._embedded?.userStoryDTOList?.map((row, index) =>
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

export default ProductBacklogBody;