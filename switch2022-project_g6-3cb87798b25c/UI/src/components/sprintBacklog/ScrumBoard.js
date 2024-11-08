import React, {useContext, useEffect, useRef, useState} from 'react';
import UserStoryBox from "./UserStoryBox";
import './ScrumBoard.css'
import AppContext from "../../context/AppContext";
import {updateUserStoryAction} from "../../context/Actions";
import UserStoryModal from "./UserStoryModal";

const ScrumBoard = () => {

    const {state, dispatch} = useContext(AppContext);
    const {sprintBacklog, selectedProject} = state;

    document.addEventListener("dragover", (event) => {
        event.preventDefault();
    });

    const initialList = [
        {title: "Open", items: []},
        {title: "Running", items: []},
        {title: "Finished", items: []}
    ]
    const [list, setList] = useState(initialList);

    const filterByStatus = (sprintBacklog, status) => {
        const newList = [];
        sprintBacklog?._embedded?.userStoryDTOList?.forEach((userStory) => {
            if (userStory.status === status) {
                const element = {
                    userStoryCode: userStory.userStoryCode.userStoryCodeValue,
                    projectCode: userStory.projectCode.projectCodeValue,
                    description: userStory.description.descriptionValue,
                    status: userStory.status
                }
                newList.push(element);
            }
        });
        return newList;
    };

    const data = [
        {title: 'Open', items: filterByStatus(sprintBacklog, "OPEN")},
        {title: 'Running', items: filterByStatus(sprintBacklog, "RUNNING")},
        {title: 'Finished', items: filterByStatus(sprintBacklog, "FINISHED")},
    ]

    useEffect(() => {
        setList(data)
    }, [sprintBacklog]);

    const dragItem = useRef();
    const dragNode = useRef();
    const [dragging, setDragging] = useState(false);

    const handleDragStart = (e, coordinates) => {
        dragItem.current = coordinates;
        dragNode.current = e.target;
        dragNode.current.addEventListener('dragend', handleDragEnd)
        //to change the style only in the component in the column instead of the
        //item being dragged
        setTimeout(() => {
            setDragging(true);
        }, 0)
    }

    const handleDragEnter = (e, coordinates) => {
        const currentItem = dragItem.current;
        if (e.target !== dragNode.current) {
            //to move the items when hovering the item over a column
            setList(oldList => {
                let newList = JSON.parse(JSON.stringify(oldList));
                newList[coordinates.columnID].items.splice
                (coordinates.itemIndex, 0, newList[currentItem.columnID].items.splice(currentItem.itemIndex, 1)[0]);
                dragItem.current = coordinates;
                return newList;
            })
        }
    }

    const handleDragEnd = () => {
        if (!dragItem.current) {
            return; // Return early if dragItem.current is not set
        }
        let column = dragItem.current.columnID;
        if (column === 0) {
            column = "OPEN";
        } else if (column === 1) {
            column = "RUNNING";
        } else if (column === 2) {
            column = "FINISHED"
        }
        const body = JSON.stringify({userStoryStatus: column});

        const projectCode = selectedProject.projectCode;

        const userStoryHtmlResult = dragNode.current.innerHTML;
        const parser = new DOMParser();
        const parsedHtml = parser.parseFromString(userStoryHtmlResult, 'text/html');
        const userStoryCode = parsedHtml.querySelector('div:nth-child(1)').textContent;

        updateUserStoryAction(dispatch,
            body,
            projectCode,
            userStoryCode)

        //reseting the values
        setDragging(false);
        dragNode.current.removeEventListener('dragend', handleDragEnd);
        dragItem.current = null;
        dragNode.current = null;
    }

    //to be able to use the 'dragging' style when dragging an item
    const getStyles = (coordinates) => {
        const item = dragItem.current;
        //if coordinates of the item correspond to the coordinates of the item set as dragging
        if (item.columnID === coordinates.columnID && item.itemIndex === coordinates.itemIndex) {
            return 'dragging item'
        }
        return 'item'
    }

    return (
        <div>
            <div className="top">
            <h2 className="scrumBoard-title">ScrumBoard</h2>
                <div className="addUS">
                    <UserStoryModal/>
                </div>
            </div>
            <div className="sprint-backlog-table">
                {list.map((column, columnID) => (
                    <div
                        key={column.title}
                        className="columns"
                        //if we are dragging and the list is empty
                        onDragEnter={dragging && !column.items.length ? (e) =>
                            handleDragEnter(e, {columnID: columnID, itemIndex: 0}) : null}>

                        <div className="column-title">{column.title}</div>
                        {column.items.map((item, itemIndex) => (

                            <div key={item.userStoryCode}
                                 onDragStart={(e) => {
                                     handleDragStart(e, {columnID: columnID, itemIndex})
                                 }}
                                 onDragEnter={dragging ? (e) => {
                                     handleDragEnter(e, {columnID: columnID, itemIndex})
                                 } : null}
                                 onDragEnd={handleDragEnd}
                                 className={dragging ? getStyles({columnID: columnID, itemIndex}) : "item"}>
                                <UserStoryBox userstory={item}/>
                            </div>
                        ))}
                    </div>
                ))}
            </div>
        </div>
    )
}
export default ScrumBoard;