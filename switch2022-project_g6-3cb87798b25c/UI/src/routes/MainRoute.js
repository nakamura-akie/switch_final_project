import Home from "../pages/Home";
import Projects from "../pages/Projects";
import Profile from "../pages/Profile";
import Dashboard from "../pages/Dashboard";
import NoMatch from "../pages/NoMatch";
import ProjectForm from "../components/project/ProjectForm";
import {Route, Routes} from "react-router-dom";
import ProjectPage from "../pages/ProjectPage";
import {useContext} from "react";
import AppContext from "../context/AppContext";
import ProductBacklogTable from "../components/productBacklog/ProductBacklogTable";
import UserStoryForm from "../components/user-story/UserStoryForm";
import SprintForm from "../components/sprint/SprintForm";
import SprintPage from "../components/sprint/SprintPage";
import Resources from "../pages/Resources";
import ResourceForm from "../components/resource/ResourceForm";
import AllSprints from "../components/sprint/AllSprints";
import SprintBacklogTable from "../components/sprintBacklog/closedSprintBacklog/SprintBacklogTable";

const MainRoute = () => {
    const {state} = useContext(AppContext);
    const {selectedProject, selectedSprint} = state;

    const {projectCode} = selectedProject;
    const {sprintCode} = selectedSprint;

    return (
        <Routes>
            <Route path="/" element={<Home/>}/>
            <Route path="home" element={<Home/>}/>
            <Route path="projects" element={<Projects/>}>
                <Route path="projectForm" element={<ProjectForm/>}/>
                <Route path={projectCode} element={<ProjectPage/>}>
                    <Route path="resources" element={<Resources/>}>
                        <Route path="resourceForm" element={<ResourceForm/>}/>
                    </Route>
                    <Route path="sprints" element={<SprintPage/>}>
                        <Route path="sprintForm" element={<SprintForm/>}>
                        </Route>
                        <Route path="all" element={<AllSprints/>}>
                            <Route path={sprintCode} element={<SprintBacklogTable />}>
                            </Route>
                        </Route>
                    </Route>
                    <Route path="productBacklog" element={<ProductBacklogTable/>}>
                        <Route path="userStoryForm" element={<UserStoryForm/>}>
                        </Route>
                    </Route>
                </Route>
            </Route>
            <Route path="profile" element={<Profile/>}/>
            <Route path="dashboard" element={<Dashboard/>}/>
            <Route path="*" element={<NoMatch/>}/>
        </Routes>
    )
}
export default MainRoute;    