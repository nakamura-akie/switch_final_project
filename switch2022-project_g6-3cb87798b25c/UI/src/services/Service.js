export const URL_API = 'http://localhost:8080';

export function fetchProjectsFromWS(success, failure) {
    const requestOptions = {
        method: 'GET',
        redirect: 'follow'
    };

    fetch(`${URL_API}/projects`, requestOptions)
        .then(response => response.json())
        .then(result => success(result))
        .catch(error => failure(error.message));
}

export function fetchProjectByCode(success, projectCode) {
    const requestOptions = {
        method: 'GET',
        redirect: 'follow'
    };

    fetch(`${URL_API}/projects/${projectCode}`, requestOptions)
        .then(response => response.json())
        .then(result => success(result))
}

export function postProject(success, failure, body) {

    let myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    let requestOptions = {
        method: 'POST',
        headers: myHeaders,
        body: JSON.stringify(body),
        redirect: 'follow'
    };

    fetch(`${URL_API}/projects`, requestOptions)
        .then(response => {
            if (!response.ok) {
                return response.json()
                    .then(body => {
                        throw new Error(body.message)
                    })
            }
        })
        .then(() => success())
        .catch(error => failure(error.message));
}

export function postUserStory(success, failure, body) {
    const requestOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: body,
        redirect: 'follow'
    };

    fetch(`${URL_API}/user-stories`, requestOptions)
        .then(response => {
            if (!response.ok) {
                return response.json()
                    .then(body => {
                        throw new Error(body.message);
                    })
            }
        })
        .then(() => success())
        .catch(error => failure(error.message));
}

export function fetchSprintBacklog(success, failure, projectCode, sprintCode) {
    const requestOptions = {
        method: 'GET',
        redirect: 'follow'
    };

    fetch(`${URL_API}/projects/${projectCode}/sprints/${sprintCode}/backlog`, requestOptions)
        .then(response => response.json())
        .then(result => success(result))
        .catch(error => failure(error.message));
}


export function fetchResourcesFromWS(success, failure, projectCode, dateOfToday) {
    const requestOptions = {
        method: 'GET',
        redirect: 'follow'
    };

    fetch(`${URL_API}/resources/${projectCode}/${dateOfToday}`, requestOptions)
        .then(response => response.json())
        .then(result => success(result))
        .catch(error => failure(error.message));
}

export function postResource(success, failure, body) {
    const requestOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: body,
        redirect: 'follow'
    };

    fetch(`${URL_API}/resources`, requestOptions)
        .then(response => {
            if (!response.ok) {
                return response.json()
                    .then(body => {
                        throw new Error(body.message)
                    })
            }
        })
        .then(() => success())
        .catch(error => failure(error.message));
}

export function fetchResourceByResourceID(success, email, projectCode, startDate, endDate) {
    const requestOptions = {
        method: 'GET',
        redirect: 'follow'
    };

    fetch(`${URL_API}/resources/${email}/${projectCode}/${startDate}/${endDate}`,
        requestOptions)
        .then(response => response.json())
        .then(result => success(result))
}

export function fetchUserStoriesFromWS(success, failure, projectCode) {
    const requestOptions = {
        method: 'GET',
        redirect: 'follow'
    };

    fetch(`${URL_API}/projects/${projectCode}/user-stories`, requestOptions)
        .then(response => response.json())
        .then(result => success(result))
        .catch(error => failure(error.message));
}

export function postSprint(success, failure, body) {
        const requestOptions = {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: body,
                redirect: 'follow'
            };

            fetch(`${URL_API}/sprints`, requestOptions)
                .then(response => {
                    if (!response.ok) {
                        return response.json()
                            .then(body => {
                                throw new Error(body.message)
                            })
                    }
                })
                .then(() => success())
                .catch(error => failure(error.message));
        }

export function fetchOpenSprint(success, failure, projectCode) {
    const requestOptions = {
        method: 'GET',
        redirect: 'follow'
    };

    fetch(`${URL_API}/projects/${projectCode}/sprints/open`, requestOptions)
        .then(response => response.json())
        .then(sprint => {
            const sprintCodeFromRequest = sprint.sprintCode;

            fetch(`${URL_API}/projects/${projectCode}/sprints/${sprintCodeFromRequest}/backlog`, requestOptions)
                .then(response => response.json())
                .then(backlog => {
                    const sprintAndBacklog = {
                        sprint, backlog
                    }
                    success(sprintAndBacklog);
                })
                .catch(error => failure(error.message));

        })
        .catch(error => failure(error.message));
}


export function fetchSprintsByProjectCode(success, failure, projectCode) {
    const requestOptions = {
        method: 'GET',
        redirect: 'follow'
    };

    fetch(`${URL_API}/projects/${projectCode}/sprints`, requestOptions)
        .then(response => response.json())
        .then(result => success(result))

}


export function fetchUserStoryByCode(success, projectCode, userStoryCode) {
    const requestOptions = {
        method: 'GET',
        redirect: 'follow'
    };

    fetch(`${URL_API}/projects/${projectCode}/user-stories/${userStoryCode}`, requestOptions)
        .then(response => response.json())
        .then(result => success(result))
}

export function updateUserStoryStatus(success, failure, projectCode, userStoryCode, body) {
    const requestOptions = {
        method: 'PATCH',
        redirect: 'follow',
        headers: {
            'Content-Type': 'application/json',
        },
        body: body
    };

    fetch(`${URL_API}/projects/${projectCode}/user-stories/${userStoryCode}`, requestOptions)
        .then(response => response.json())
        .then(result => success(result))
        .catch(error => console.log('error', error));
}

export function updateSprintBacklog(success, failure, projectCode, sprintCode, body) {
    const requestOptions = {
        method: 'PATCH',
        redirect: 'follow',
        headers: {
            'Content-Type': 'application/json',
        },
        body: body
    };

    fetch(`${URL_API}/projects/${projectCode}/sprints/${sprintCode}/backlog`, requestOptions)
        .then(response => {
            if (!response.ok) {
                return response.json()
                    .then(body => {
                        throw new Error(body.message)
                    })
            }
        })
        .then(() => success())
        .catch(error => failure(error.message));
}

export function updateSprintStatus(success, failure, body) {
    const requestOptions = {
        method: 'PATCH',
        redirect: 'follow',
        headers: {
            'Content-Type': 'application/json',
        },
        body: body
    };

    fetch(`${URL_API}/sprints`, requestOptions)
        .then(response => response.json())
        .then(result => success(result))
        .catch(error => console.log('error', error));
}
