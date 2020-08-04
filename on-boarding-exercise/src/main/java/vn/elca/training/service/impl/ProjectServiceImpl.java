package vn.elca.training.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.elca.training.dao.ProjectRepository;
import vn.elca.training.dom.Project;
import vn.elca.training.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }
}