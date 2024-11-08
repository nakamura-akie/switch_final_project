import React, {useReducer} from 'react';
import PropTypes from "prop-types";
import {Provider} from './AppContext';
import reducer from './Reducer';
import * as AiIcons from "react-icons/ai";


const menus = [
    {
        key: "/home",
        label: "Home",
        icon: <AiIcons.AiFillHome/>,
        cName: "nav-text",
    },
    {
        key: "/projects",
        label: "Projects",
        icon: <AiIcons.AiFillProfile/>,
        cName: "nav-text",
    },
];

const nav = {
    selectedMenu: menus[0],
    menus: menus,
};

const projectList = [];

const roleList = [
    { name: "TEAM_MEMBER" },
    { name: "SCRUM_MASTER" },
    { name: "PRODUCT_OWNER" },
    { name: "PROJECT_MANAGER" }
]

const accountsList = [
    {
        accountName: "João Silva",
        email: "js@mymail.com",
        accountPhoneNumber: "915879652",
        accountProfile: "user",
        accountStatus: "true"
    },
    {
        accountName: "Manel Costa",
        email: "ms@mymail.com",
        accountPhoneNumber: "926650520",
        accountProfile: "user",
        accountStatus: "true"
    },
    {
        accountName: "Xico Ferreira",
        email: "xf@mymail.com",
        accountPhoneNumber: "963650532",
        accountProfile: "user",
        accountStatus: "true"
    },
    {
        accountName: "teste",
        email: "teste@mymail.com",
        accountPhoneNumber: "915879652",
        accountProfile: "user",
        accountStatus: "true"
    }
]

const projectListHeaders = {
    header1: "Project Code", header2: "Project Name",
    header3: "Customer", header4: "Status"
};

const resourceTableHeaders = {
    header1: "Resource Email",
    header2: "Project Code",
    header3: "Project Role",
    header4: "Start Date",
    header5: "End Date"
};

const sprintListHeader = {
header1: "Sprint Code", header2: "Start Date", header3: "End Date", header4: "Status"
};

const customers = [
    {
        name: "XPTO, SA",
        tin: "211267490"
    },
    {
        name: "XYZ, Lda",
        tin: "272983330"
    },
    {
        name: "Hell, LLC",
        tin: "235140236"
    }
]

const businessSectors = [
         { name: "Hospitality industry" },
         { name: "Technology" },
         { name: "Marketing" }
]

const projectTypologies = [
         { name: "Fixed-Cost" },
         { name: "Time and Materials" }
]

const productBacklogHeaders = {
    header1: "User Story Code", header2: "Project Code", header3: "Description", header4: "Status"
}


const selectedProject = {
    projectCode: "default",
    projectName: "default",
    description: "default",
    customerID: "default",
    status: "default",
    businessSectorName: "default",
    projectTypologyName: "default",
    budget: "default",
    numberPlannedSprints: "default",
    sprintDuration: "default",
    period: {
        startDate: "default",
        endDate: "default"
    }
};

const selectedUserStory = {
    projectCode: "default",
    userStoryCode: "default",
    userStoryDescription: "default",
    userStoryStatus: "default",
    userStoryEffort: 1
}

const openSprint = {
    sprintCode: '',
    startDate: '',
    endDate: '',
    sprintStatus: ''
}

const selectedSprint = {
    sprintCode: "default",
    startDate: "default",
    endDate: "default",
    sprintStatus: "default"
}

const resourcesList = [];

const selectedResource = {
    resourceEmail: "default",
    resourceProjectCode: "default",
    resourceStartDate: "default",
    resourceEndDate: "default",
    resourceProjectRole: "default",
    resourcePercentageOfAllocation: "default",
    resourceCostPerHour: "default",
}

const sprintBacklog = [];
const userStoryList = [];

const AppData = {
    applicationState: {
        projectSuccess: false,
        resourceSuccess: false,
        resourceFailure: false,
        projectFailure: false,
        userStoryFailure: false,
        userStorySuccess: false,
        userStoryMessage: "",
        sprintSuccess: false,
        sprintFailure: false,
        sprintCreationSuccess: false,
        sprintCreationFailure: false,
        sprintMessage: "",
        error: "",
        sprintBacklogMessage: false
    },
    nav: nav,
    projectList: projectList,
    customers: customers,
    businessSectors: businessSectors,
    projectTypologies: projectTypologies,
    projectListHeaders: projectListHeaders,
    selectedProject: selectedProject,
    productBacklogHeaders: productBacklogHeaders,
    sprintList: [],
    sprintListHeader: sprintListHeader,
    roleList: roleList,
    accountsList: accountsList,
    userStoryList,
    resourcesList: resourcesList,
    selectedUserStory: selectedUserStory,
    resourceTableHeaders: resourceTableHeaders,
    sprintBacklog,
    selectedResource: selectedResource,
    openSprint: openSprint,
    selectedSprint: selectedSprint
}

const AppProvider = (props) => {

    const initialState = AppData;

    const [state, dispatch] = useReducer(reducer, initialState);
    return (
        <Provider value={{
            state,
            dispatch
        }}>
            {props.children}
        </Provider>
    );
};
AppProvider.propTypes = {
    children: PropTypes.node,
};

export default AppProvider;