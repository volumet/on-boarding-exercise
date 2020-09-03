package vn.elca.training.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.elca.training.repository.ProjectRepository;
import vn.elca.training.model.entity.Project;
import vn.elca.training.service.ProjectService;

/**
 * @author vlp
 *
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }
}
