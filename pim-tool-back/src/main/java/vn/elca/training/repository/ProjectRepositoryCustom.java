package vn.elca.training.repository;

import vn.elca.training.model.entity.Project;

import java.util.List;


public interface ProjectRepositoryCustom {
    List<Project> getListProject();
    List<Project> getProjectByNumber(Long proNum);
    void deleteOneProject(Project proNum);
    Project getProjectBySingleNumber(Long proNum);
}
