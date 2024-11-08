import AppContext from "../../context/AppContext";
import React, {useContext} from "react";
import buttonStyles from "./../Button.module.css";
import Button from "./../Button";
import {useNavigate} from "react-router-dom";
import {getProjectByProjectCode} from "../../context/Actions";
import styles from "./ProjectListTable.module.css"

const ProjectsListTableBody = (props) => {
    const {state, dispatch} = useContext(AppContext);
    const {customers} = state;
    const navigate = useNavigate();


    const rows = props.filteredProjects?.map((row, index) =>
        <tr key={index} className={styles.rows}>
            <td className={styles.projectCodeRow}>{row.projectCode}</td>
            <td>{row.projectName}</td>
            <td>{customers.find(customer => customer.tin === row.projectCustomer).name}</td>
            <td>{row.projectStatus}</td>
            <td className={styles.buttonCell}>
                <Button style={buttonStyles.button} onClick={() => {
                    getProjectByProjectCode(dispatch, row.projectCode);
                    navigate("/projects/" + row.projectCode);
                }}> View Project</Button>
            </td>
        </tr>
    );

    return (
        <tbody className="projectsTableBody">
        {rows}
        </tbody>
    );
};

export default ProjectsListTableBody;
