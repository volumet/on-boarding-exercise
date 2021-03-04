package vn.elca.training.repository;

import vn.elca.training.model.dto.SearchDto;
import vn.elca.training.model.entity.Project;

import java.util.List;
import java.util.Optional;


public interface ProjectRepositoryCustom {
    Optional<Project> getProjectByNumber(Long proNum);
    Long checkVersion(Long proNum);
    Long checkProjectNumberExist(Long proNum);
    Project getNewProjectByNumber(Long proNum);
    List<Project> searchProject(SearchDto searchDto);
}
