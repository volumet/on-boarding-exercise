package vn.elca.training.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.elca.training.model.dto.DeleteProjectMapDto;
import vn.elca.training.model.dto.ProjectReqDto;
import vn.elca.training.model.dto.SearchDto;
import vn.elca.training.model.entity.Employee;
import vn.elca.training.model.entity.Group;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.enums.ProjectStatus;
import vn.elca.training.model.error.ProjectServiceErrorMessage;
import vn.elca.training.model.exception.PimBusinessException;
import vn.elca.training.model.validator.PimValidator;
import vn.elca.training.repository.EmployeeRepository;
import vn.elca.training.repository.GroupRepository;
import vn.elca.training.repository.ProjectRepository;
import vn.elca.training.service.ProjectService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.stream.Collectors;

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
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Project> getListProject() {
        return projectRepository.findAll().stream().sorted(Comparator.comparing(Project::getProjectNumber)).collect(Collectors.toList());
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
        em.detach(project);
        mapper.projectReqDtoToProjectForEdit(project, projectReqDto);

        Group group = groupRepository.getOne(projectReqDto.getGroupId());
        project.setGroup(group);

        List<Employee> employees = employeeRepository.findEmployeeByVisaIn(projectReqDto.getEmployeeVisa());
        project.clearEmployees();
        project.setEmployees(new HashSet<>(employees));

        project.setVersion(projectReqDto.getVersion());
        projectRepository.save(project);
    }

    @Override
    public void deleteOneProject(Long projectReqDto) {
        Project project = projectRepository.getProjectByNumber(projectReqDto)
                .orElseThrow(() -> new PimBusinessException(ProjectServiceErrorMessage.PROJECT_NUMBER_MUST_BE_EXIST));
        projectRepository.delete(project);
    }

    public void validateProjectNumExistForMultiProjects(int foundSize, int requestSize) {
        //Project number edited exist
        validator.validateTrue(foundSize == requestSize,
                ProjectServiceErrorMessage.PROJECT_NUMBER_MUST_BE_EXIST);
    }

    public void validateProjectStatusNew(Project project) {
        validator.validateTrue(project.getStatus().equals(ProjectStatus.NEW),
                ProjectServiceErrorMessage.PROJECT_STATUS_MUST_BE_NEW);
    }

    @Override
    public void deleteMultipleProjects(DeleteProjectMapDto deleteProjectMapDto) {
        List<Project> toBeDeletedList = projectRepository
                .getProjectByProjectNumberIn(deleteProjectMapDto.getListProjectNum());
        validateProjectNumExistForMultiProjects(toBeDeletedList.size(),
                deleteProjectMapDto.getListProjectNum().size());
        toBeDeletedList.forEach(this::validateProjectStatusNew);

        projectRepository.deleteAll(toBeDeletedList);
    }

    @Override
    public Project getAProject(ProjectReqDto projectReqDto) {
        return projectRepository.getProjectByNumber(projectReqDto.getProjectNumber())
                .orElseThrow(() -> new PimBusinessException(ProjectServiceErrorMessage.PROJECT_NUMBER_MUST_BE_EXIST));
    }

    @Override
    public List<Project> searchListProject(SearchDto searchDto) {
        return projectRepository.searchProject(searchDto)
                .stream()
                .sorted(Comparator.comparing(Project::getProjectNumber))
                .collect(Collectors.toList());
    }
}
