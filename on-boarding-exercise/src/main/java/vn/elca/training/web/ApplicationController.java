package vn.elca.training.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.service.ProjectService;
import vn.elca.training.util.Mapper;

@Controller
public class ApplicationController {

    private ProjectService projectService;
    
    @Value("${total.number.of.projects}")
    private String message;

    @RequestMapping("/")
    public ModelAndView main() {
        Map<String, Object> model = new HashMap<String, Object>() {
            private static final long serialVersionUID = -6883088231537577238L;
            {
                put("title", "Project Management Tool");
                put("message", String.format(message, projectService.findAll().size()));
            }
        };
        return new ModelAndView("search", model);
    }

    @RequestMapping("/query")
    @ResponseBody
    public List<ProjectDto> query() {
        return projectService.findAll()
                .stream()
                .map(Mapper::projectToProjectDto)
                .collect(Collectors.toList());
    }
}
