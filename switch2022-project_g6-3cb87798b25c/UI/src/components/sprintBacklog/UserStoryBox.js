import React, {useContext, useState} from 'react';
import {FaEllipsisV} from "react-icons/fa";
import UserStoryDetailsModal from "./UserStoryDetailsModal";
import {getUserStoryByUserStoryCode} from "../../context/Actions";
import AppContext from "../../context/AppContext";
import styles from "./UserStoryBox.module.css"

const UserStoryBox = ({userstory}) => {

    const [showModal, setShowModal] = useState(false);
    const {dispatch} = useContext(AppContext);


    return (
        <div draggable className={styles.draggable}>
            <div>{userstory.userStoryCode}</div>
            <div>{userstory.description}</div>
            <div className={styles.details}>
                <FaEllipsisV
                    onClick={() => {
                        setShowModal(true);
                        getUserStoryByUserStoryCode(dispatch, userstory.projectCode, userstory.userStoryCode);
                    }}>Show Details</FaEllipsisV>

                <UserStoryDetailsModal showModal={showModal} onClose={() => setShowModal(false)}/>
            </div>
        </div>
    )
}

export default UserStoryBox;