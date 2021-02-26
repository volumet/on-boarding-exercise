package vn.elca.training.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.elca.training.model.dto.DeleteProjectMapDto;
import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.dto.ProjectReqDto;
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
                .map(mapper::projectToProjectDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/delete")
    public String delete(@RequestBody ProjectReqDto projectReqDto) {
        projectService.deleteOneProject(projectReqDto);
        return "deleted";
    }

    @PostMapping("/multipleDelete")
    public String multipleDelete(@RequestBody DeleteProjectMapDto deleteProjectMapDto) {
        projectService.deleteMultipleProjects(deleteProjectMapDto);
        return "deleted";
    }
}
