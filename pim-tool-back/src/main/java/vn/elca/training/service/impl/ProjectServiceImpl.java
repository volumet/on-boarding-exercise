package vn.elca.training.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.elca.training.model.dto.DeleteProjectMapDto;
import vn.elca.training.model.dto.ProjectReqDto;
import vn.elca.training.model.entity.Employee;
import vn.elca.training.model.entity.Group;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.error.ProjectServiceErrorMessage;
import vn.elca.training.model.validator.PimValidator;
import vn.elca.training.repository.EmployeeRepository;
import vn.elca.training.repository.GroupRepository;
import vn.elca.training.repository.ProjectRepository;
import vn.elca.training.service.ProjectService;

import java.util.*;

/**
 * @author vlp
 */
@Service
@Primary
@Profile("dev")
@Transactional
public class ProjectServiceImpl extends AbstractService implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private PimValidator validator;

    @Override
    public List<Project> getListProject() {
        return projectRepository.findAll();
    }

    public void validateNotNull(ProjectReqDto projectReqDto) {

        //Project number not null
        validator.validateNotNull(projectReqDto.getProjectNumber(), ProjectServiceErrorMessage.FIELD_MUST_NOT_BE_NULL);

        //Project name not empty
        validator.validateNotEmpty(projectReqDto.getProjectName(), ProjectServiceErrorMessage.FIELD_MUST_NOT_BE_NULL);

        //Customer not empty
        validator.validateNotEmpty(projectReqDto.getCustomer(), ProjectServiceErrorMessage.FIELD_MUST_NOT_BE_NULL);

        //Group id not null
        validator.validateNotNull(projectReqDto.getGroupId(), ProjectServiceErrorMessage.FIELD_MUST_NOT_BE_NULL);

        //Status not null
        validator.validateNotNull(projectReqDto.getStatus(), ProjectServiceErrorMessage.FIELD_MUST_NOT_BE_NULL);

        //Start date not null
        validator.validateNotNull(projectReqDto.getStartDate(), ProjectServiceErrorMessage.FIELD_MUST_NOT_BE_NULL);

    }

    public void validateEmployeeExist(ProjectReqDto projectReqDto) {
        //All employee visas exist
        validator.validateTrue(
                (employeeRepository
                        .searchEmployeeByVisa(projectReqDto.getEmployeeVisa())
                        == projectReqDto.getEmployeeVisa().size()),
                ProjectServiceErrorMessage.MEMBER_MUST_BE_EXIST);
    }

    public void validateProjectNumDuplicate(ProjectReqDto projectReqDto) {
        //Project number created not duplicate
        validator.validateNull(projectRepository.getProjectByNumber(projectReqDto.getProjectNumber()),
                ProjectServiceErrorMessage.PROJECT_NUMBER_MUST_NOT_BE_DUPLICATE);
    }

    public void validateProjectNumExist(ProjectReqDto projectReqDto) {
        //Project number edited exist
        validator.validateNotNull(projectRepository.getProjectByNumber(projectReqDto.getProjectNumber()),
                ProjectServiceErrorMessage.PROJECT_NUMBER_MUST_BE_EXIST);
    }

    public void validateStartDateBeforeEndDate(ProjectReqDto projectReqDto) {
        //Start date must be before end date
        if (projectReqDto.getEndDate() != null) {
            validator.validateTrue(projectReqDto.getStartDate().isBefore(projectReqDto.getEndDate()),
                    ProjectServiceErrorMessage.DEADLINE_MUST_NOT_BE_GREATER_THAN_PROJECT_FINISHING_DATE);
        }
    }

    @Override
    public void createNewProject(ProjectReqDto projectReqDto) {
        validateNotNull(projectReqDto);
        validateProjectNumDuplicate(projectReqDto);
        validateEmployeeExist(projectReqDto);
        validateStartDateBeforeEndDate(projectReqDto);

        Project project = mapper.projectReqDtoToProject(projectReqDto);
        Group group = groupRepository.getOne(projectReqDto.getGroupId());
        project.setGroup(group);
        List<Employee> employees = employeeRepository.findEmployeeByVisaIn(projectReqDto.getEmployeeVisa());
        project.setEmployees(new HashSet<>(employees));
        projectRepository.save(project);
    }

    @Override
    public void editProject(ProjectReqDto projectReqDto) {
        validateNotNull(projectReqDto);
        validateProjectNumExist(projectReqDto);
        validateEmployeeExist(projectReqDto);
        validateStartDateBeforeEndDate(projectReqDto);


        Project dummyProject = mapper.projectReqDtoToProject(projectReqDto);
        Project project = projectRepository.getProjectByNumber(dummyProject.getProjectNumber());
        mapper.projectReqDtoToProjectForEdit(project, projectReqDto);

        Group group = groupRepository.getOne(projectReqDto.getGroupId());
        project.setGroup(group);

        List<Employee> employees = employeeRepository.findEmployeeByVisaIn(projectReqDto.getEmployeeVisa());
        project.clearEmployees();
        project.setEmployees(new HashSet<>(employees));

        projectRepository.save(project);
    }

    @Override
    public void deleteOneProject(ProjectReqDto projectReqDto) {
        validateProjectNumExist(projectReqDto);

        Project dummyProject = mapper.projectReqDtoToProject(projectReqDto);
        Project project = projectRepository.getProjectByNumber(dummyProject.getProjectNumber());
        projectRepository.deleteOneProject(project);
    }

    public void validateProjectNumExistForMultiProjects(Long projectNum) {
        //Project number edited exist
        validator.validateTrue(projectRepository.getProjectBySingleNumber(projectNum) != null,
                ProjectServiceErrorMessage.PROJECT_NUMBER_MUST_BE_EXIST);
    }

    @Override
    public void deleteMultipleProjects(DeleteProjectMapDto deleteProjectMapDto) {
        List<Long> listProjectNumber = new ArrayList<>();
        for (Map.Entry<Long, Boolean> project : deleteProjectMapDto.getListProjectNum().entrySet()) {
            if (project.getValue()) {
                listProjectNumber.add(project.getKey());
            }
        }
        for (Long projectNum : listProjectNumber) {
            validateProjectNumExistForMultiProjects(projectNum);
        }
        for (Long projectNum : listProjectNumber) {
            projectRepository.deleteOneProject(projectRepository
                    .getProjectBySingleNumber(projectNum));
        }
    }


}
