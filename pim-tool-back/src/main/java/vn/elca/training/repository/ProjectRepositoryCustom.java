package vn.elca.training.repository;

import vn.elca.training.model.entity.Project;

import java.util.Optional;


public interface ProjectRepositoryCustom {
    Optional<Project> getProjectByNumber(Long proNum);
    Long checkProjectNumberExist(Long proNum);
    Project getNewProjectByNumber(Long proNum);
}
