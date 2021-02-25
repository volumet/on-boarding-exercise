package vn.elca.training.repository;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.querydsl.jpa.impl.JPAQuery;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import vn.elca.training.ApplicationWebConfig;
import vn.elca.training.PimTestConfiguration;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.entity.QProject;

@ActiveProfiles({"dev","unit-test"})
@ContextConfiguration(classes = {PimTestConfiguration.class})
@RunWith(value=SpringRunner.class)
public class ProjectRepositoryTest {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private ProjectRepository projectRepository;

    @Test
    public void testGetListProject() {
        List<Project> projectList = projectRepository.getListProject();
        Assert.assertTrue(projectList.size() > 0);
    }
}
