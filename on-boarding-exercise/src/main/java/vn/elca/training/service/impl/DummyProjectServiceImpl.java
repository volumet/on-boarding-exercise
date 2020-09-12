package vn.elca.training.service.impl;

import org.springframework.stereotype.Service;
import vn.elca.training.model.entity.Project;
import vn.elca.training.service.ProjectService;

import java.util.List;

/**
 * @author gtn
 *
 */
@Service
public class DummyProjectServiceImpl implements ProjectService {
    @Override
    public List<Project> findAll() {
        throw new UnsupportedOperationException("This is a dummy service");
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("This is a dummy service");
    }
}
