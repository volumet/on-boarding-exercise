package vn.elca.training.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import vn.elca.training.PimTestConfiguration;
import vn.elca.training.model.dto.DeleteProjectMapDto;
import vn.elca.training.model.dto.ProjectReqDto;
import vn.elca.training.model.entity.Employee;
import vn.elca.training.model.entity.Group;
import vn.elca.training.model.exception.PimBusinessException;
import vn.elca.training.repository.EmployeeRepository;
import vn.elca.training.repository.GroupRepository;
import vn.elca.training.repository.ProjectRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;

@ActiveProfiles({"dev", "unit-test"})
@ContextConfiguration(classes = {PimTestConfiguration.class})
@RunWith(value = SpringRunner.class)
@Transactional
public class ProjectServiceTest {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectService projectService;

    @Test
    public void testCreateNewProjectValid() {
        Employee employee = new Employee("VNQ", "Vinh", "Nguyen", Date.valueOf(LocalDate.now()));
        employeeRepository.save(employee);
        Group group = new Group();
        group.setLeader(employee);
        groupRepository.save(group);

        ProjectReqDto projectReqDto = new ProjectReqDto(1L,
                "Project 1",
                "Anh A", 1L,
                Arrays.asList("VNQ"),
                "New",
                LocalDate.now(),
                LocalDate.now().plusDays(1));

        projectService.createNewProject(projectReqDto);
        Assert.assertTrue(!projectRepository.findAll().isEmpty());
    }

    @Test(expected = PimBusinessException.class)
    public void testCreateNewProjectFieldEmpty() {
        Employee employee = new Employee("VNQ", "Vinh", "Nguyen", Date.valueOf(LocalDate.now()));
        employeeRepository.save(employee);
        Group group = new Group();
        group.setLeader(employee);
        groupRepository.save(group);

        ProjectReqDto projectReqDto = new ProjectReqDto(1L,
                "",
                "Anh A", 1L,
                Arrays.asList("VNQ"),
                "New",
                LocalDate.now(),
                LocalDate.now().plusDays(1));

        projectService.createNewProject(projectReqDto);
    }

    @Test(expected = PimBusinessException.class)
    public void testCreateNewProjectMemberExist() {
        Employee employee = new Employee("VNQ", "Vinh", "Nguyen", Date.valueOf(LocalDate.now()));
        employeeRepository.save(employee);
        Group group = new Group();
        group.setLeader(employee);
        groupRepository.save(group);

        ProjectReqDto projectReqDto = new ProjectReqDto(1L,
                "Project 1",
                "Anh A", 1L,
                Arrays.asList("InvalidMember"),
                "New",
                LocalDate.now(),
                LocalDate.now().plusDays(1));

        projectService.createNewProject(projectReqDto);
    }

    @Test(expected = PimBusinessException.class)
    public void testCreateNewProjectDuplicateProjectName() {
        Employee employee = new Employee("VNQ", "Vinh", "Nguyen", Date.valueOf(LocalDate.now()));
        employeeRepository.save(employee);
        Group group = new Group();
        group.setLeader(employee);
        groupRepository.save(group);

        ProjectReqDto projectReqDto = new ProjectReqDto(1L,
                "Project 1",
                "Anh A", 1L,
                Arrays.asList("VNQ"),
                "New",
                LocalDate.now(),
                LocalDate.now().plusDays(1));

        projectService.createNewProject(projectReqDto);

        projectReqDto = new ProjectReqDto(1L,
                "Project 2",
                "Anh A", 1L,
                Arrays.asList("VNQ"),
                "New",
                LocalDate.now(),
                LocalDate.now().plusDays(1));

        projectService.createNewProject(projectReqDto);
    }

    @Test(expected = PimBusinessException.class)
    public void testCreateNewProjectInvalidDate() {
        Employee employee = new Employee("VNQ", "Vinh", "Nguyen", Date.valueOf(LocalDate.now()));
        employeeRepository.save(employee);
        Group group = new Group();
        group.setLeader(employee);
        groupRepository.save(group);

        ProjectReqDto projectReqDto = new ProjectReqDto(1L,
                "Project 1",
                "Anh A", 1L,
                Arrays.asList("VNQ"),
                "New",
                LocalDate.now(),
                LocalDate.now().minusDays(1));

        projectService.createNewProject(projectReqDto);
    }

    @Test
    public void testEditProjectValid() {
        Employee employee = new Employee("VNQ", "Vinh", "Nguyen", Date.valueOf(LocalDate.now()));
        employeeRepository.save(employee);
        Group group = new Group();
        group.setLeader(employee);
        groupRepository.save(group);

        ProjectReqDto projectReqDto = new ProjectReqDto(1L,
                "Project 1",
                "Anh A", 1L,
                Arrays.asList("VNQ"),
                "New",
                LocalDate.now(),
                LocalDate.now().plusDays(1));

        projectService.createNewProject(projectReqDto);

        projectReqDto = new ProjectReqDto(1L,
                "Project 2",
                "Anh A", 1L,
                Arrays.asList("VNQ"),
                "New",
                LocalDate.now(),
                LocalDate.now().plusDays(1));
        projectService.editProject(projectReqDto);
        Assert.assertEquals(projectRepository.getOne(1L).getName(), "Project 2");
    }

    @Test(expected = PimBusinessException.class)
    public void testEditProjectProjectNumNotExist() {
        Employee employee = new Employee("VNQ", "Vinh", "Nguyen", Date.valueOf(LocalDate.now()));
        employeeRepository.save(employee);
        Group group = new Group();
        group.setLeader(employee);
        groupRepository.save(group);

        ProjectReqDto projectReqDto = new ProjectReqDto(1L,
                "Project 1",
                "Anh A", 1L,
                Arrays.asList("VNQ"),
                "New",
                LocalDate.now(),
                LocalDate.now().plusDays(1));

        projectService.createNewProject(projectReqDto);

        projectReqDto = new ProjectReqDto(2L,
                "Project 2",
                "Anh A", 1L,
                Arrays.asList("VNQ"),
                "New",
                LocalDate.now(),
                LocalDate.now().plusDays(1));
        projectService.editProject(projectReqDto);
    }

    @Test
    public void testDeleteOneProject() {
        Employee employee = new Employee("VNQ", "Vinh", "Nguyen", Date.valueOf(LocalDate.now()));
        employeeRepository.save(employee);
        Group group = new Group();
        group.setLeader(employee);
        groupRepository.save(group);

        ProjectReqDto projectReqDto = new ProjectReqDto(1L,
                "Project 1",
                "Anh A", 1L,
                Arrays.asList("VNQ"),
                "New",
                LocalDate.now(),
                LocalDate.now().plusDays(1));

        projectService.createNewProject(projectReqDto);

        projectReqDto = new ProjectReqDto(1L,
                "Project 2",
                "Anh A", 1L,
                Arrays.asList("VNQ"),
                "New",
                LocalDate.now(),
                LocalDate.now().plusDays(1));
        projectService.deleteOneProject(projectReqDto);

        Assert.assertNull(projectRepository.getNewProjectByNumber(1L));
    }

    @Test
    public void testDeleteMultipleProjects() {
        Employee employee = new Employee("VNQ", "Vinh", "Nguyen", Date.valueOf(LocalDate.now()));
        employeeRepository.save(employee);
        Group group = new Group();
        group.setLeader(employee);
        groupRepository.save(group);

        ProjectReqDto projectReqDto = new ProjectReqDto(1L,
                "Project 1",
                "Anh A", 1L,
                Arrays.asList("VNQ"),
                "New",
                LocalDate.now(),
                LocalDate.now().plusDays(1));

        projectService.createNewProject(projectReqDto);

        projectReqDto = new ProjectReqDto(2L,
                "Project 2",
                "Anh B", 1L,
                Arrays.asList("VNQ"),
                "New",
                LocalDate.now(),
                LocalDate.now().plusDays(1));

        projectService.createNewProject(projectReqDto);

        DeleteProjectMapDto deleteProjectMapDto = new DeleteProjectMapDto();
        deleteProjectMapDto.getListProjectNum().add(1L);
        deleteProjectMapDto.getListProjectNum().add(2L);

        projectService.deleteMultipleProjects(deleteProjectMapDto);

        Assert.assertTrue(projectRepository.findAll().isEmpty());
    }
}
