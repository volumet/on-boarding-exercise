package vn.elca.training.service;

import java.util.List;

import org.springframework.stereotype.Service;
import vn.elca.training.model.dto.DeleteProjectMapDto;
import vn.elca.training.model.dto.ProjectReqDto;
import vn.elca.training.model.dto.SearchDto;
import vn.elca.training.model.entity.Project;

/**
 * @author vlp
 *
 */
@Service
public interface ProjectService {
    List<Project> getListProject();
    void createNewProject(ProjectReqDto projectReqDto);
    void editProject(ProjectReqDto projectReqDto);
    void deleteOneProject(Long projectReqDto);
    void deleteMultipleProjects(DeleteProjectMapDto deleteProjectMapDto);
    Project getAProject(ProjectReqDto projectReqDto);
    List<Project> searchListProject(SearchDto searchDto);
}
