package vn.elca.training.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.elca.training.dao.IProjectRepository;
import vn.elca.training.dom.Project;

@Service
public class ProjectService implements IProjectService {
    
    private IProjectRepository projectRepository;

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }
}
