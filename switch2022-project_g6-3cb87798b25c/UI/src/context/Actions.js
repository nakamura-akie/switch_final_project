import {
    fetchProjectByCode,
    fetchProjectsFromWS,
    fetchSprintsByProjectCode,
    fetchUserStoriesFromWS,
    fetchResourcesFromWS,
    postProject,
    postResource,
    postSprint,
    postUserStory,
    fetchSprintBacklog,
    fetchUserStoryByCode,
    updateUserStoryStatus,
    updateSprintBacklog,
    fetchOpenSprint,
    fetchResourceByResourceID,
    updateSprintStatus
} from "../services/Service";

export const SELECT_MENU = 'SELECT_MENU';
export const SELECT_PROJECT = 'SELECT_PROJECT';
export const SELECT_RESOURCE ='SELECT_RESOURCE';
export const CREATE_PROJECT = 'CREATE_PROJECT';
export const CREATE_USER_STORY = 'CREATE_USER_STORY';
export const CREATE_SPRINT = 'CREATE_SPRINT';
export const CREATE_RESOURCE = 'CREATE_RESOURCE';
export const FETCH_PROJECTS_SUCCESS = 'FETCH_PROJECTS_SUCCESS';
export const FETCH_PROJECTS_FAILURE = 'FETCH_PROJECTS_FAILURE';
export const SET_PROJECT_SUCCESS = 'SET_PROJECT_SUCCESS';
export const SET_PROJECT_FAILURE = 'SET_PROJECT_FAILURE';
export const POST_PROJECT_SUCCESS = 'POST_PROJECT_SUCCESS';
export const POST_PROJECT_FAILURE = 'POST_PROJECT_FAILURE';
export const GET_PROJECTBYCODE_SUCCESS = 'GET_PROJECTBYCODE_SUCCESS';
export const POST_USERSTORY_FAILURE = 'POST_USERSTORY_FAILURE';
export const POST_USERSTORY_SUCCESS = 'POST_USERSTORY_SUCCESS';
export const GET_RESOURCEBYRESOURCEID_SUCCESS = 'GET_RESOURCEBYRESOURCEID_SUCCESS';
export const FETCH_SPRINTBACKLOG_SUCCESS = 'FETCH_SPRINTBACKLOG_SUCCESS';
export const FETCH_SPRINTBACKLOG_FAILURE = 'FETCH_SPRINTBACKOG_FAILURE';
export const POST_RESOURCE_FAILURE = 'POST_RESOURCE_FAILURE';
export const POST_RESOURCE_SUCCESS = 'POST_RESOURCE_SUCCESS';
export const SET_RESOURCE_SUCCESS = 'SET_RESOURCE_SUCCESS';
export const SET_RESOURCE_FAILURE = 'SET_RESOURCE_FAILURE';
export const FETCH_RESOURCES_SUCCESS = 'FETCH_RESOURCES_SUCCESS';
export const FETCH_RESOURCES_FAILURE = 'FETCH_RESOURCES_FAILURE';
export const FETCH_USERSTORIES_FAILURE = 'FETCH_USERSTORIES_FAILURE';
export const FETCH_USERSTORIES_SUCCESS = 'FETCH_USERSTORIES_SUCCESS';
export const SET_USERSTORY_FAILURE = 'SET_USERSTORY_FAILURE';
export const SET_USERSTORY_SUCCESS = 'SET_USERSTORY_SUCCESS';
export const GET_USERSTORYBYCODE_SUCCESS = 'GET_USERSTORYBYCODE_SUCCESS';
export const POST_SPRINT_FAILURE = 'POST_SPRINT_FAILURE';
export const POST_SPRINT_SUCCESS = 'POST_SPRINT_SUCCESS';
export const SET_SPRINT_SUCCESS = 'SET_SPRINT_SUCCESS';
export const SET_SPRINT_FAILURE = 'SET_SPRINT_FAILURE';
export const FETCH_SPRINTS_FAILURE = 'FETCH_SPRINTS_FAILURE';
export const FETCH_SPRINTS_SUCCESS = 'FETCH_SPRINTS_SUCCESS';
export const PATCH_USERSTORY_SUCCESS = 'PATCH_USERSTORY_SUCCESS';
export const PATCH_USERSTORY_FAILURE = 'PATCH_USERSTORY_FAILURE';
export const FETCH_OPENSPRINT_SUCCESS = 'FETCH_OPENSPRINT_SUCCESS';
export const FETCH_OPENSPRINT_FAILURE = 'FETCH_OPENSPRINT_FAILURE';
export const PATCH_SPRINTBACKLOG_SUCCESS = 'PATCH_SPRINTBACKLOG_SUCCESS';
export const PATCH_SPRINTBACKLOG_FAILURE = 'PATCH_SPRINTBACKLOG_FAILURE';
export const SET_SPRINTBACKLOGPATCH_MESSAGE = 'SET_SPRINTBACKLOGPATCH_MESSAGE';
export const SET_SPRINT_CREATION_FAILURE = 'SET_SPRINT_CREATION_FAILURE';
export const SET_SPRINT_CREATION_SUCCESS = 'SET_SPRINT_CREATION_SUCCESS';
export const CLOSE_SPRINT = 'CLOSE_SPRINT';
export const SELECT_SPRINT = 'SELECT_SPRINT';

export function fetchProjects(dispatch) {
    fetchProjectsFromWS((res) => dispatch(fetchProjectsSuccess(res)), (err) => dispatch(fetchProjectsFailure(err)));
}

function fetchProjectsSuccess(projects) {
    return {
        type: FETCH_PROJECTS_SUCCESS,
        payload: {
            data: projects
        }
    }
}

function fetchProjectsFailure(message) {
    return {
        type: FETCH_PROJECTS_FAILURE,
        payload: {
            error: message
        }
    }
}

export function postProjectAction(dispatch, projectBody) {
    postProject(
        () => dispatch(postProjectSuccess()),
        (error) => dispatch(postProjectFailure(error)),
        projectBody
    )
}

export function setProjectSuccess(dispatch, value) {
    const action = {
        type: SET_PROJECT_SUCCESS,
        payload: {
            state: value
        }
    }
    dispatch(action);
}

export function setUserStorySuccess(dispatch, value) {
    const action = {
        type: SET_USERSTORY_SUCCESS,
        payload: {
            state: value
        }
    }
    dispatch(action);
}

export function setProjectFailure(dispatch, value) {
    const action = {
        type: SET_PROJECT_FAILURE,
        payload: {
            state: value
        }
    }
    dispatch(action);
}

export function setUserStoryFailure(dispatch, value) {
    const action = {
        type: SET_USERSTORY_FAILURE,
        payload: {
            state: value
        }
    }
    dispatch(action);
}

function postProjectSuccess() {
    return {
        type: POST_PROJECT_SUCCESS,
    }
}

function postProjectFailure(message) {
    return {
        type: POST_PROJECT_FAILURE,
        payload: {
            error: message
        }
    }
}

export function getProjectByProjectCode(dispatch, projectCode) {
    fetchProjectByCode((data) => dispatch(getProjectByCodeSuccess(data)), projectCode)
}

function getProjectByCodeSuccess(data) {
    return {
        type: GET_PROJECTBYCODE_SUCCESS,
        payload: {
            data: data
        }
    }
}

export function postUserStoryAction(dispatch, body) {
    postUserStory(
        () => dispatch(postUserStorySuccess()),
        (error) => dispatch(postUserStoryFailure(error)),
        body
    );
}

function postUserStorySuccess(body) {
    return {
        type: POST_USERSTORY_SUCCESS,
        payload: {
            body: body
        }
    }
}

function postUserStoryFailure(message) {
    return {
        type: POST_USERSTORY_FAILURE,
        payload: {
            error: message
        }
    }
}

export function getSprintBacklog(dispatch, projectCode, sprintCode) {
    fetchSprintBacklog(
        (res) => dispatch(fetchSprintBacklogSuccess(res)),
        (err) => dispatch(fetchSprintBacklogFailure(err)),
        projectCode,
        sprintCode);
}

function fetchSprintBacklogSuccess(sprintBacklog) {

    return {
        type: FETCH_SPRINTBACKLOG_SUCCESS,
        payload: {
            data: sprintBacklog
        }
    }
}

function fetchSprintBacklogFailure(message) {
    return {
        type: FETCH_SPRINTBACKLOG_FAILURE,
        payload: {
            error: message
        }
    }
}

export function postResourceAction(dispatch, body) {
    postResource(
        () => dispatch(postResourceSuccess()),
        (error) => dispatch(postResourceFailure(error)),
        body
    );
}

function postResourceSuccess() {
    return {
        type: POST_RESOURCE_SUCCESS,
    }
}

function postResourceFailure(message) {
    return {
        type: POST_RESOURCE_FAILURE,
        payload: {
            error: message
        }
    }
}

export function setResourceSuccess(dispatch, value) {
    const action = {
        type: SET_RESOURCE_SUCCESS,
        payload: {
            state: value
        }
    }
    dispatch(action);
}

export function setResourceFailure(dispatch, value) {
    const action = {
        type: SET_RESOURCE_FAILURE,
        payload: {
            state: value
        }
    }
    dispatch(action);
}

export function fetchResources(dispatch, projectCode, date) {
    fetchResourcesFromWS(
        (res) => dispatch(fetchResourcesSuccess(res)),
        (err) => dispatch(fetchResourcesFailure(err)),
        projectCode,
        date);
}

function fetchResourcesSuccess(resources) {
    return {
        type: FETCH_RESOURCES_SUCCESS,
        payload: {
            data: resources
        }
    }
}

function fetchResourcesFailure(message) {
    return {
        type: FETCH_RESOURCES_FAILURE,
        payload: {
            error: message
        }
    }
}

export function getResourceByResourceID(dispatch, email, projectCode, startDate, endDate) {
    fetchResourceByResourceID((data) =>
            dispatch(getResourceByResourceIdSuccess(data)),
        email,
        projectCode,
        startDate,
        endDate)
}

function getResourceByResourceIdSuccess(data) {
    return {
        type: GET_RESOURCEBYRESOURCEID_SUCCESS,
        payload: {
            data: data
        }
    }
}

export function selectResource(dispatch, resource) {
    const action = {
        type: SELECT_RESOURCE,
        payload: {
            resource: resource,
        },
    };
    dispatch(action);
}


export function fetchUserStories(dispatch, projectCode) {
    //const url = `projects/userstories?projectCode=${encodeURIComponent(projectCode)}`;

    fetchUserStoriesFromWS(
        (data) => dispatch(fetchUserStoriesSuccess(data)),
        (err) => dispatch(fetchUserStoriesFailure(err)),
        projectCode);
}

function fetchUserStoriesSuccess(data) {
    return {
        type: FETCH_USERSTORIES_SUCCESS,
        payload: {
            data: data
        }
    }
}

function fetchUserStoriesFailure(message) {
    return {
        type: FETCH_USERSTORIES_FAILURE,
        payload: {
            error: message
        }
    }
}


export function selectMenu(dispatch, name) {
    const action = {
        type: SELECT_MENU,
        payload: {
            key: name
        }
    }
    dispatch(action)
}

export function selectProject(dispatch, project) {
    const action = {
        type: SELECT_PROJECT,
        payload: {
            project: project,
        },
    };
    dispatch(action);
}

export function saveProject(dispatch, project) {
    const action = {
        type: CREATE_PROJECT,
        payload: {
            project: project,
        },
    };
    dispatch(action);
}

export function saveResource(dispatch, resource) {
    const action = {
        type: CREATE_RESOURCE,
        payload: {
            project: resource,
        },
    };
    dispatch(action);
}

export function saveUserStory(dispatch, newUserStory) {
    const action = {
        type: CREATE_USER_STORY,
        payload: {
            userStory: newUserStory,
        },
    };
    dispatch(action)
}

export function saveSprint(dispatch, newSprint) {
    const action = {
        type: CREATE_SPRINT,
        payload: {
            sprint: newSprint,
        },
    };
    dispatch(action)
}

export function closeSprint(dispatch, openSprintToClose) {
    const action = {
        type: CLOSE_SPRINT,
        payload: {
            sprint: openSprintToClose,
        },
    };
    dispatch(action)
}

export function postSprintAction(dispatch, body) {
    postSprint(
        (data) => dispatch(postSprintSuccess(data)),
        (error) => dispatch(postSprintFailure(error)),
        body
    );
}

function postSprintSuccess(data) {
    return {
        type: POST_SPRINT_SUCCESS,
        payload: {
            data: data
        }
    }
}

function postSprintFailure(message) {
    return {
        type: POST_SPRINT_FAILURE,
        payload: {
            error: message
        }
    }
}

export function setSprintSuccess(value) {
    return {
        type: SET_SPRINT_SUCCESS,
        payload: {
            state: value
        }
    }
}

export function setSprintFailure(value) {
    return {
        type: SET_SPRINT_FAILURE,
        payload: {
            state: value
        }
    }
}

export function getOpenSprint(dispatch, projectCode) {
    fetchOpenSprint(
        (res) => dispatch(fetchOpenSprintSuccess(res)),
        (err) => dispatch(fetchOpenSprintFailure(err)),
        projectCode);
}

function fetchOpenSprintSuccess(data) {
    return {
        type: FETCH_OPENSPRINT_SUCCESS,
        payload: {
            data: data
        }
    }
}

function fetchOpenSprintFailure(message) {
    return {
        type: FETCH_OPENSPRINT_FAILURE,
        payload: {
            error: message
        }
    }
}

export function updateSprintStatusAction(dispatch, body) {
    updateSprintStatus(
        (info) => dispatch(setSprintSuccess(info)),
        (error) => dispatch(setSprintFailure(error)),
        body
    );
}


export function getSprintsByProjectCode(dispatch, projectCode) {
    fetchSprintsByProjectCode(
        (res) => dispatch(fetchSprintsSuccess(res)),
        (err) => dispatch(fetchSprintsFailure(err)),
        projectCode);
}

function fetchSprintsSuccess(openSprint) {
    return {
        type: FETCH_SPRINTS_SUCCESS,
        payload: {
            data: openSprint
        }
    }
}

function fetchSprintsFailure(message) {
    return {
        type: FETCH_SPRINTS_FAILURE,
        payload: {
            error: message
        }
    }
}
export function getUserStoryByUserStoryCode(dispatch, projectCode, userStoryCode) {
    fetchUserStoryByCode((data) => dispatch(getUserStoryByCodeSuccess(data)), projectCode, userStoryCode)
}

function getUserStoryByCodeSuccess(data) {
    return {
        type: GET_USERSTORYBYCODE_SUCCESS,
        payload: {
            data: data
        }
    }
}

export function updateUserStoryAction(dispatch, body, projectCode, userStoryCode) {
    updateUserStoryStatus(
        (info) => dispatch(patchUserStorySuccess(info)),
        (error) => dispatch(patchUserStoryFailure(error)),
        projectCode,
        userStoryCode,
        body
    );
}

function patchUserStorySuccess(data) {
    return {
        type: PATCH_USERSTORY_SUCCESS,
        payload: {
            data: data
        }
    }
}

function patchUserStoryFailure(message) {
    return {
        type: PATCH_USERSTORY_FAILURE,
        payload: {
            error: message
        }
    }
}

export function selectSprint(dispatch, sprint) {
    const action = {
        type: SELECT_SPRINT,
        payload: {
            data: sprint,
        },
    };
    dispatch(action);
}

export function updateSprintBacklogAction(dispatch, projectCode, sprintCode, body) {
    updateSprintBacklog(
        () => dispatch(patchSprintBacklogSuccess()),
        (error) => dispatch(patchSprintBacklogFailure(error)),
        projectCode,
        sprintCode,
        body
    );
}

function patchSprintBacklogSuccess(body) {
    return {
        type: PATCH_SPRINTBACKLOG_SUCCESS,
        payload: {
            body: body
        }
    }
}

function patchSprintBacklogFailure(message) {
    return {
        type: PATCH_SPRINTBACKLOG_FAILURE,
        payload: {
            error: message
        }
    }
}


export function setSprintCreationSuccess(dispatch, value) {
    const action = {
        type: SET_SPRINT_CREATION_SUCCESS,
        payload: {
            state: value
        }
    }
    dispatch(action);
}


export function setSprintCreationFailure(dispatch, value) {
    const action = {
        type: SET_SPRINT_CREATION_FAILURE,
        payload: {
            state: value
        }
    }
    dispatch(action);
}