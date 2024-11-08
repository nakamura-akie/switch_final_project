import TableHeader from "./ProductBacklogHeader";
import TableBody from "./ProductBacklogBody";
import React, {Fragment, useContext, useState} from 'react';
import styles from "./ProductBacklogTable.module.css"
import Button from "../Button";
import buttonStyles from "../Button.module.css";
import AppContext from "../../context/AppContext";
import {Outlet, useLocation, useNavigate} from "react-router-dom";

const ProductBacklogTable = () => {
    const {state} = useContext(AppContext);
    const {selectedProject} = state;
    const {projectCode} = selectedProject;
    const location = useLocation();

    const navigate = useNavigate();

    const handleClick = (event) => {
        event.preventDefault();
        navigate("/projects/" + projectCode + "/productBacklog/userStoryForm");
    }

    return (
        <Fragment>
            {location.pathname === "/projects/" + projectCode + "/productBacklog" ?
                <Fragment>
                    <div>
                        <h1>{selectedProject.projectName} - Product Backlog</h1>
                        <Button style={buttonStyles.button} onClick={handleClick}>Create User Story</Button>
                        <table className={styles.productBacklogTable}>
                            <TableHeader/>
                            <TableBody projectCode={projectCode}/>
                        </table>
                    </div>
                </Fragment> : <Outlet/>}
        </Fragment>
    )
};

export default ProductBacklogTable;