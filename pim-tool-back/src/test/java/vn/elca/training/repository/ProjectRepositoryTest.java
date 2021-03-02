package vn.elca.training.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import vn.elca.training.PimTestConfiguration;
import vn.elca.training.model.entity.Employee;
import vn.elca.training.model.entity.Group;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.enums.ProjectStatus;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@ActiveProfiles({"dev", "unit-test"})
@ContextConfiguration(classes = {PimTestConfiguration.class})
@RunWith(value = SpringRunner.class)
@Transactional
public class ProjectRepositoryTest {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private GroupRepository groupRepository;


    @Test
    public void testGetProjectByNumber() {
        Employee employee = new Employee("VNQ", "Vinh", "Nguyen", Date.valueOf(LocalDate.now()));
        employeeRepository.save(employee);
        Group group = new Group();
        group.setLeader(employee);
        groupRepository.save(group);
        Project project = new Project(1L, "Project A", "Anh A", ProjectStatus.NEW,
                LocalDate.now(), LocalDate.now().plusDays(1));
        project.setGroup(group);
        projectRepository.save(project);
        Assert.assertNotNull(projectRepository.getProjectByNumber(1L));
    }

    @Test
    public void testDeleteOneProject() {
        Employee employee = new Employee("VNQ", "Vinh", "Nguyen", Date.valueOf(LocalDate.now()));
        employeeRepository.save(employee);
        Group group = new Group();
        group.setLeader(employee);
        groupRepository.save(group);
        Project project = new Project(1L, "Project A", "Anh A", ProjectStatus.NEW,
                LocalDate.now(), LocalDate.now().plusDays(1));
        project.setGroup(group);

        Set<Employee> employeeSet = new HashSet<>();
        employeeSet.add(employee);

        project.setEmployees(employeeSet);
        projectRepository.save(project);
        projectRepository.deleteOneProject(project);
        Assert.assertNull(projectRepository.getProjectByNumber(1L));
    }

    @Test
    public void testGetProjectBySingleNumber() {
        Employee employee = new Employee("VNQ", "Vinh", "Nguyen", Date.valueOf(LocalDate.now()));
        employeeRepository.save(employee);
        Group group = new Group();
        group.setLeader(employee);
        groupRepository.save(group);
        Project project = new Project(1L, "Project A", "Anh A", ProjectStatus.NEW,
                LocalDate.now(), LocalDate.now().plusDays(1));
        project.setGroup(group);
        projectRepository.save(project);
        Assert.assertNotNull(projectRepository.getNewProjectByNumber(1L));
    }

    @Test
    public void testCheckProjectNumberExist() {
        Employee employee = new Employee("VNQ", "Vinh", "Nguyen", Date.valueOf(LocalDate.now()));
        employeeRepository.save(employee);
        Group group = new Group();
        group.setLeader(employee);
        groupRepository.save(group);
        Project project = new Project(1L, "Project A", "Anh A", ProjectStatus.NEW,
                LocalDate.now(), LocalDate.now().plusDays(1));
        project.setGroup(group);
        projectRepository.save(project);

        Assert.assertTrue(projectRepository.checkProjectNumberExist(1L) > 0);
    }
}
