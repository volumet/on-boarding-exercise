package vn.elca.training.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.service.ProjectService;
import vn.elca.training.util.Mapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gtn
 *
 */
@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/search")
    public List<ProjectDto> search() {
        return projectService.findAll()
                .stream()
                .map(Mapper::projectToProjectDto)
                .collect(Collectors.toList());
    }
}
