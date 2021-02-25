package vn.elca.training.util;

import org.springframework.stereotype.Component;
import vn.elca.training.model.dto.EmployeeDto;
import vn.elca.training.model.dto.GroupDto;
import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.dto.ProjectReqDto;
import vn.elca.training.model.entity.Employee;
import vn.elca.training.model.entity.Group;
import vn.elca.training.model.entity.Project;

import java.util.stream.Collectors;

/**
 * @author gtn
 */
@Component
public class ApplicationMapper {
    public ApplicationMapper() {
        // Mapper utility class
    }

    public ProjectDto projectToProjectDto(Project entity) {
        ProjectDto dto = new ProjectDto();
        dto.setId(entity.getId());
        dto.setProjectNumber(entity.getProjectNumber());
        dto.setName(entity.getName());
        dto.setCustomer(entity.getCustomer());
        dto.setStatus(convertToLongStatus(entity.getStatus()));
        dto.setStartDate(entity.getStartDate());

        return dto;
    }

    public EmployeeDto employeeToEmployeeDto(Employee entity) {
        EmployeeDto dto = new EmployeeDto();
        dto.setVisa(entity.getVisa());
        dto.setFullName(entity.getFirstName()
                .concat(" ")
                .concat(entity.getLastName()));

        return dto;
    }

    public GroupDto groupToGroupDto(Group entity) {
        GroupDto dto = new GroupDto();
        dto.setName(entity.getId());
        dto.setLeaderVisa(entity
                .getLeader()
                .getVisa());

        return dto;
    }

    public String convertToShortStatus(String status) {
        switch(status) {
            case "New":
                return "NEW";
            case "Planned":
                return "PLA";
            case "In Progress":
                return "INP";
            case "Finished":
                return "FIN";
        }
        return null;
    }

    public String convertToLongStatus(String status) {
        switch(status) {
            case "NEW":
                return "New";
            case "PLA":
                return "Planned";
            case "INP":
                return "In Progress";
            case "FIN":
                return "Finished";
        }
        return null;
    }

    public Project projectReqDtoToProject(ProjectReqDto entity) {
        Project project = new Project();
        project.setProjectNumber(entity.getProjectNumber());
        project.setName(entity.getProjectName());
        project.setCustomer(entity.getCustomer());
        project.setStatus(convertToShortStatus(entity.getStatus()));
        project.setStartDate(entity.getStartDate());
        project.setEndDate(entity.getEndDate());

        return project;
    }

    public void projectReqDtoToProjectForEdit(Project project, ProjectReqDto entity) {
        project.setName(entity.getProjectName());
        project.setCustomer(entity.getCustomer());
        project.setStatus(convertToShortStatus(entity.getStatus()));
        project.setStartDate(entity.getStartDate());
        project.setEndDate(entity.getEndDate());
    }
}
