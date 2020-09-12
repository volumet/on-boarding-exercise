package vn.elca.training.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import vn.elca.training.model.entity.Project;
import vn.elca.training.repository.ProjectRepository;
import vn.elca.training.service.ProjectService;

import java.util.List;

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

    @Override
    public long count() {
        return projectRepository.count();
    }
}
