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
import vn.elca.training.model.exception.PimBusinessException;
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
        validator.validateTrue(projectRepository.checkProjectNumberExist(projectReqDto.getProjectNumber()) <= 0,
                ProjectServiceErrorMessage.PROJECT_NUMBER_MUST_NOT_BE_DUPLICATE);
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
        validateEmployeeExist(projectReqDto);
        validateStartDateBeforeEndDate(projectReqDto);

        Project project = projectRepository.getProjectByNumber(projectReqDto.getProjectNumber())
                .orElseThrow(() -> new PimBusinessException(ProjectServiceErrorMessage.PROJECT_NUMBER_MUST_BE_EXIST));
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
        Project project = projectRepository.getProjectByNumber(projectReqDto.getProjectNumber())
                .orElseThrow(() -> new PimBusinessException(ProjectServiceErrorMessage.PROJECT_NUMBER_MUST_BE_EXIST));
        projectRepository.deleteOneProject(project);
    }

    public void validateProjectNumExistForMultiProjects(Long projectNum) {
        //Project number edited exist
        validator.validateTrue(projectRepository.getNewProjectByNumber(projectNum) != null,
                ProjectServiceErrorMessage.PROJECT_NUMBER_MUST_BE_EXIST);
    }

    @Override
    public void deleteMultipleProjects(DeleteProjectMapDto deleteProjectMapDto) {
        List<Long> listProjectNumber = deleteProjectMapDto.getListProjectNum();

        for (Long projectNum : listProjectNumber) {
            validateProjectNumExistForMultiProjects(projectNum);
        }

        //TODO Delete in
        for (Long projectNum : listProjectNumber) {
            projectRepository.deleteOneProject(projectRepository
                    .getNewProjectByNumber(projectNum));
        }
    }


}
