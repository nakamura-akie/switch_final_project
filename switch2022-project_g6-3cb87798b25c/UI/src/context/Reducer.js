
import {
    CREATE_PROJECT,
    CREATE_RESOURCE,
    FETCH_OPENSPRINT_FAILURE,
    FETCH_OPENSPRINT_SUCCESS,
    FETCH_PROJECTS_FAILURE,
    FETCH_PROJECTS_SUCCESS,
    FETCH_RESOURCES_FAILURE,
    FETCH_RESOURCES_SUCCESS,
    FETCH_SPRINTBACKLOG_FAILURE,
    FETCH_SPRINTBACKLOG_SUCCESS,
    FETCH_SPRINTS_FAILURE,
    FETCH_SPRINTS_SUCCESS,
    FETCH_USERSTORIES_FAILURE,
    FETCH_USERSTORIES_SUCCESS,
    GET_PROJECTBYCODE_SUCCESS,
    GET_RESOURCEBYRESOURCEID_SUCCESS,
    GET_USERSTORYBYCODE_SUCCESS,
    PATCH_SPRINTBACKLOG_FAILURE,
    PATCH_SPRINTBACKLOG_SUCCESS,
    PATCH_USERSTORY_FAILURE,
    PATCH_USERSTORY_SUCCESS,
    POST_PROJECT_FAILURE,
    POST_PROJECT_SUCCESS,
    POST_RESOURCE_FAILURE,
    POST_RESOURCE_SUCCESS,
    POST_SPRINT_FAILURE,
    POST_SPRINT_SUCCESS,
    POST_USERSTORY_FAILURE,
    POST_USERSTORY_SUCCESS,
    SELECT_MENU,
    SELECT_PROJECT,
    SELECT_RESOURCE,
    SELECT_SPRINT,
    SET_PROJECT_FAILURE,
    SET_PROJECT_SUCCESS,
    SET_RESOURCE_FAILURE,
    SET_RESOURCE_SUCCESS,
    SET_SPRINT_FAILURE,
    SET_SPRINT_SUCCESS,
    SET_SPRINT_CREATION_FAILURE,
    SET_SPRINT_CREATION_SUCCESS,
    SET_SPRINTBACKLOGPATCH_MESSAGE, SET_USERSTORY_FAILURE, SET_USERSTORY_SUCCESS,
} from './Actions'

const reducer = (state, action) => {
    switch (action.type) {
        case SELECT_MENU:
            const key = action.payload.key;
            const {nav} = state;
            const menu = nav.menus.find((item) => (item.key === key));
            const newNav = {...nav, selectedMenu: menu}

            return {
                ...state,
                nav: newNav,
            };
        case SELECT_PROJECT:
            return ({
                ...state,
                selectedProject: action.payload.project
            });

        case SELECT_RESOURCE:
            return ({
                ...state,
                selectedResource: action.payload.resource
            });

        case CREATE_PROJECT:
            const project = action.payload.project;
            return ({
                ...state,
                projectList: [...state.projectList, project]

            });

        case CREATE_RESOURCE:
            const resource = action.payload.resource;
            return ({
                ...state,
                resourcesList: [...state.resourcesList, resource]

            });

        case SET_SPRINT_SUCCESS:
            return ({
                ...state,
                applicationState: {
                    ...state.applicationState,
                    sprintSuccess: action.payload.state
                }
            })


        case POST_SPRINT_SUCCESS:
            const sprintData = action.payload.data;
            return ({
                ...state,
                sprintData: sprintData,
                applicationState: {
                    ...state.applicationState,
                    sprintCreationSuccess: true,
                    sprintMessage: "Sprint Added Successfully"
                }
            })

        case POST_SPRINT_FAILURE:
            const sprintError = action.payload.error;

            return ({
                 ...state,
                  applicationState: {
                  ...state.applicationState,
                  sprintCreationFailure: true,
                  sprintMessage: sprintError
                  }
            })

        case SET_SPRINT_CREATION_SUCCESS:
            return ({
                ...state,
                applicationState: {
                    ...state.applicationState,
                    sprintCreationSuccess: action.payload.state
                }
            })

        case SET_SPRINT_CREATION_FAILURE:
             const sprintValue = action.payload.state;

             return ({
                ...state,
                applicationState: {
                     ...state.applicationState,
                     sprintCreationFailure: sprintValue
                }
             })



        case FETCH_PROJECTS_SUCCESS:
            const projectList = action.payload.data;
            return ({
                ...state,
                projectList: projectList
            })

        case FETCH_PROJECTS_FAILURE:
            const error = action.payload.error;

            return ({
                ...state,
                error: error
            })

        case POST_PROJECT_SUCCESS:
            return ({
                ...state,
                applicationState: {
                    ...state.applicationState,
                    projectSuccess: true
                }
            })

        case SET_PROJECT_SUCCESS:
            return ({
                ...state,
                applicationState: {
                    ...state.applicationState,
                    projectSuccess: action.payload.state
                }
            })

        case SET_PROJECT_FAILURE:
            const value = action.payload.state;

            return ({
                ...state,
                applicationState: {
                    ...state.applicationState,
                    projectFailure: value
                }
            })

        case POST_PROJECT_FAILURE:
            const postProjectError = action.payload.error;

            return ({
                ...state,
                applicationState: {
                    ...state.applicationState,
                    error: postProjectError,
                    projectFailure: true
                }
            })

        case GET_PROJECTBYCODE_SUCCESS:
            return ({
                ...state,
                selectedProject: action.payload.data
            })

        case POST_USERSTORY_SUCCESS:
            return ({
                ...state,
                userStoryList: action.payload.body,
                applicationState: {
                    ...state.applicationState,
                    userStorySuccess: true,
                    userStoryMessage: "The User Story was successfully added to the Product Backlog"
                }
            })

        case POST_USERSTORY_FAILURE:
            const displayMessage = action.payload.error;

            return ({
                ...state,
                applicationState: {
                    ...state.applicationState,
                    userStoryFailure: true,
                    userStoryMessage: displayMessage
                }
            })

        case SET_USERSTORY_FAILURE:
            const failure = action.payload.state;

            return ({
                ...state,
                applicationState: {
                    ...state.applicationState,
                    userStoryFailure: failure
                }
            })

        case SET_USERSTORY_SUCCESS:
            return ({
                ...state,
                applicationState: {
                    ...state.applicationState,
                    userStorySuccess: action.payload.state
                }
            })

        case FETCH_USERSTORIES_SUCCESS:
            const userStoryList = action.payload.data;
            return ({
                ...state,
                userStoryList: userStoryList
            })

        case FETCH_USERSTORIES_FAILURE:
            const fetchError = action.payload.error;

            return ({
                ...state,
                error: fetchError
            })

        case POST_RESOURCE_SUCCESS:
            return ({
                ...state,
                applicationState: {
                    ...state.applicationState,
                    resourceSuccess: true
                }
            })

        case POST_RESOURCE_FAILURE:
            const resourcePostError = action.payload.error;

            return ({
                ...state,
                applicationState: {
                    ...state.applicationState,
                    error: resourcePostError,
                    resourceFailure: true
                }
            })

        case SET_RESOURCE_SUCCESS:
            return ({
                ...state,
                applicationState: {
                    ...state.applicationState,
                    resourceSuccess: action.payload.state
                }
            })

        case SET_RESOURCE_FAILURE:
            const info = action.payload.state;

            return ({
                ...state,
                applicationState: {
                    ...state.applicationState,
                    resourceFailure: info
                }

            })

        case FETCH_RESOURCES_SUCCESS:
            const resourcesList = action.payload.data;
            return ({
                ...state,
                resourcesList: resourcesList
            })

        case FETCH_RESOURCES_FAILURE:
            const resourceFetchError = action.payload.error;

            return ({
                ...state,
                error: resourceFetchError
            })

        case FETCH_SPRINTBACKLOG_SUCCESS:
            const sprintBacklogList = action.payload.data;
            return ({
                ...state,
                sprintBacklog: sprintBacklogList
            })

        case FETCH_SPRINTBACKLOG_FAILURE:
            const sprintBacklogError = action.payload.error;
            return ({
                ...state,
                error: sprintBacklogError
            })

        case GET_USERSTORYBYCODE_SUCCESS:
            return ({
                ...state,
                selectedUserStory: action.payload.data
            })
        case PATCH_USERSTORY_SUCCESS:
            return ({
                ...state,
                selectedUserStory: action.payload.data
            })
        case PATCH_USERSTORY_FAILURE:
            const updateUserStoryStateError = action.payload.error;
            return ({
                ...state,
                error: updateUserStoryStateError
            })

        case PATCH_SPRINTBACKLOG_SUCCESS:
            return ({
                ...state,
                sprintBacklog: action.payload.body,
                applicationState: {
                    ...state.applicationState,
                    sprintBacklogMessage: "The selected User Story was successfully added to the Sprint Backlog"
                }
            })

        case PATCH_SPRINTBACKLOG_FAILURE:
            const message = action.payload.error;

            return ({
                ...state,
                applicationState: {
                    ...state.applicationState,
                    sprintBacklogMessage: message
                }
            })

        case SET_SPRINTBACKLOGPATCH_MESSAGE:
            return ({
                ...state,
                applicationState: {
                    ...state.applicationState,
                    sprintBacklogMessage: action.payload.message
                }
            })

        case FETCH_OPENSPRINT_SUCCESS:
            const openSprintFromBackend = action.payload.data.sprint;
            const backlogFromBackend = action.payload.data.backlog;
            return ({
                ...state,
                openSprint: openSprintFromBackend,
                sprintBacklog: backlogFromBackend
            })

        case FETCH_OPENSPRINT_FAILURE:
            const openSprintError = action.payload.error;
            return ({
                ...state,
                error: openSprintError
            })

        case FETCH_SPRINTS_SUCCESS:
            const sprintListFromBackend = action.payload.data;
            return ({
                ...state,
                sprintList: sprintListFromBackend
            })

        case FETCH_SPRINTS_FAILURE:
            const sprintListError = action.payload.error;
            return ({
                ...state,
                error: sprintListError
            })

        case SELECT_SPRINT:
            const selectedSprintFromTable = action.payload.data;
            return ({
                ...state,
                selectedSprint: selectedSprintFromTable
            })


        case GET_RESOURCEBYRESOURCEID_SUCCESS:
            return ({
                ...state,
                selectedResource: action.payload.data
            })

        default:
            return state;

    }
};

export default reducer;
