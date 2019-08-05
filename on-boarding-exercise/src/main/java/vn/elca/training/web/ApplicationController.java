package vn.elca.training.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import vn.elca.training.dom.Project;
import vn.elca.training.service.IProjectService;

@Controller
public class ApplicationController {
    
    private IProjectService projectService;
    
    @Value("${total.number.of.projects}")
    private String message;

    @RequestMapping("/")
    ModelAndView main() {
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
    List<Project> query() {
        return projectService.findAll();
    }
}
