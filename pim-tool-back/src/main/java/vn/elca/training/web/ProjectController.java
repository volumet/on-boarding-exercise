package vn.elca.training.web;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
public class ProjectController extends AbstractApplicationController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/new")
    public String insert(@RequestBody ProjectReqDto projectReqDto) {
        projectService.createNewProject(projectReqDto);
        return "inserted";
    }

    @PostMapping("/edit")
    public String edit(@RequestBody ProjectReqDto projectReqDto) throws SQLServerException {
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
}
