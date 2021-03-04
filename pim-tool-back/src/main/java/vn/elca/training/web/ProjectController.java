package vn.elca.training.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.elca.training.model.dto.DeleteProjectMapDto;
import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.dto.ProjectReqDto;
import vn.elca.training.model.dto.SearchDto;
import vn.elca.training.model.entity.Project;
import vn.elca.training.service.ProjectService;

import java.util.List;
import java.util.stream.Collectors;



/**
 * @author gtn
 */
@RestController
@RequestMapping("/projects")
@CrossOrigin
public class ProjectController extends AbstractApplicationController {

    @Autowired
    private ProjectService projectService;


    @PutMapping("/new")
    public String insert(@RequestBody ProjectReqDto projectReqDto) {
        projectService.createNewProject(projectReqDto);
        return "inserted";
    }

    @PutMapping("/edit")
    public String edit(@RequestBody ProjectReqDto projectReqDto) {
        projectService.editProject(projectReqDto);
        return "edited";
    }

    @GetMapping("/load")
    public List<ProjectDto> load() {
        return projectService.getListProject()
                .stream()
                .limit(300)
                .map(mapper::projectToProjectDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/delete/{projectNumber}")
    public String delete(@PathVariable("projectNumber") Long projectNumber) {
        projectService.deleteOneProject(projectNumber);
        return "deleted";
    }

    @DeleteMapping("/multipleDelete")
    public String multipleDelete(@RequestBody DeleteProjectMapDto deleteProjectMapDto) {
        projectService.deleteMultipleProjects(deleteProjectMapDto);
        return "deleted";
    }

    @PostMapping("/singleLoad")
    public Project singleLoad(@RequestBody ProjectReqDto projectReqDto) {
        return projectService.getAProject(projectReqDto);
    }

    @PostMapping("/search")
    public List<ProjectDto> search(@RequestBody SearchDto searchDto) {
        return projectService.searchListProject(searchDto)
                .stream()
                .limit(300)
                .map(mapper::projectToProjectDto)
                .collect(Collectors.toList());
    }
}

