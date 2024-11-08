import React, {useContext, useEffect, useRef, useState} from 'react';
import AppContext from "../../context/AppContext";
import {fetchUserStories} from "../../context/Actions";
import searchStyles from "./SearchBox.module.css";
import Button from "../Button";
import buttonStyles from "../Button.module.css"

const SearchBox = (props) => {
    const {state, dispatch} = useContext(AppContext);
    const [filteredData, setFilteredData] = useState([]);
    const [searchValue, setSearchValue] = useState('');
    const [selectedResult, setSelectedResult] = useState(null);
    const [selectedIndex, setSelectedIndex] = useState(-1);
    const {selectedProject} = state;
    const {projectCode} = selectedProject;
    const {userStoryList} = state;
    const selectedOptionRef = useRef(null);
    const {sprintBacklog} = state;
    const [code, setCode] = useState(null);

    useEffect(() => {
        fetchUserStories(dispatch, projectCode);
    }, []);

    const handleKeyDown = (event) => {
        if (event.key === 'ArrowDown') {
            event.preventDefault();
            setSelectedIndex((prevIndex) => {
                if (prevIndex === -1 || prevIndex === filteredData.length - 1) {
                    return 0;
                } else {
                    return prevIndex + 1;
                }
            });
        } else if (event.key === 'ArrowUp') {
            event.preventDefault();
            setSelectedIndex((prevIndex) => {
                if (prevIndex === 0 || prevIndex === -1) {
                    return filteredData.length - 1;
                } else {
                    return prevIndex - 1;
                }
            });
        } else if (event.key === 'Enter' && selectedIndex !== -1) {
            event.preventDefault();
            setSelectedResult(filteredData[selectedIndex]);
            setFilteredData([]);
            setSearchValue("");
            handleResultSelection(filteredData[selectedIndex].userStoryCode.userStoryCodeValue);
        }
    };

    useEffect(() => {
        if (selectedOptionRef.current) {
            selectedOptionRef.current.scrollIntoView({
                behavior: 'smooth',
                block: 'nearest',
            });
        }
    }, [selectedIndex]);

    useEffect(() => {
        document.addEventListener('keydown', handleKeyDown);

        return () => {
            document.removeEventListener('keydown', handleKeyDown);
        };
    }, [selectedIndex]);

    const handleFilter = (event) => {
        const searchWord = event.target.value.toLowerCase();
        setSearchValue(searchWord);

        const newUserStoryList = userStoryList?._embedded?.userStoryDTOList || [];
        const newSprintBacklog = sprintBacklog?._embedded?.userStoryDTOList || [];

        const filteredData = newUserStoryList.filter((userStory) => {
            const isRepeated = newSprintBacklog.some(
                (sprintUserStory) => sprintUserStory.userStoryCode.userStoryCodeValue
                    === userStory.userStoryCode.userStoryCodeValue
            );
            const userStoryCode = userStory.userStoryCode.userStoryCodeValue.toLowerCase();
            const description = userStory.description.descriptionValue.toLowerCase();
            return !isRepeated && (userStoryCode.includes(searchWord) || description.includes(searchWord));
        });

        if (searchWord === "") {
            setFilteredData([]);
            setSelectedResult(null);
            setCode(null);
        } else {
            const filteredDataByInput = filteredData.filter((userStory) => {
                const userStoryCode = userStory.userStoryCode.userStoryCodeValue.toLowerCase();
                const description = userStory.description.descriptionValue.toLowerCase();
                return userStoryCode.includes(searchWord) || description.includes(searchWord);
            });
            setFilteredData(filteredDataByInput);
            setSelectedIndex(-1);
        }
    };

    const handleResultSelection = (result) => {
        setFilteredData([]);
        setSearchValue("");
        setCode(result);
    };

    const handleReset = () => {
        setSelectedResult(null);
        setFilteredData([]);
        setSearchValue('');
        document.getElementById('search-input').value = '';
    };

    return (
        <div className={searchStyles.search}>
            <div>
                <div>
                    <input id="search-input"
                           className={searchStyles.searchbox}
                           type="text"
                           placeholder="US..."
                           onChange={handleFilter}
                    />
                </div>
                {filteredData.length > 0 && (
                    <div className={searchStyles.searchResult}>
                        {filteredData.map((value, index) => (
                            <div
                                ref={index === selectedIndex ? selectedOptionRef : null}
                                key={index}
                                onClick={() => {
                                    setSelectedIndex(index);
                                    setSelectedResult(value);
                                    handleResultSelection(value.userStoryCode.userStoryCodeValue);
                                }}
                                onMouseEnter={() => setSelectedIndex(index)}
                                className={index === selectedIndex ? searchStyles.selected : ''}>
                                <p>{value.userStoryCode.userStoryCodeValue} - {value.description.descriptionValue}</p>
                            </div>
                        ))}
                    </div>
                )}
            </div>
            {filteredData.length === 0 && !selectedResult && (
                <div className={searchStyles.noMatchFound}>
                    <p>No match found</p>
                </div>
            )}
            {selectedResult && (
                <div>
                    <div className={searchStyles.selectedResultContainer}>
                        <table>
                            <thead>
                            <tr>
                                <th>Your selection: {selectedResult.userStoryCode.userStoryCodeValue}</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                            </tr>
                            <tr>
                                <td>{selectedResult.description.descriptionValue}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <div className={searchStyles.buttonContainer}>
                        <Button style={buttonStyles.reset_button} onClick={handleReset}>
                            Clear
                        </Button>
                        <Button style={buttonStyles.confirm_button} onClick={() => props.onResultSelection(code)}>
                            Add
                        </Button>
                    </div>
                </div>
            )}
        </div>
    );
};

export default SearchBox;