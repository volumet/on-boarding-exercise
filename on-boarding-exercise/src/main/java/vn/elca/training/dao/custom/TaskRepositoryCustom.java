package vn.elca.training.dao.custom;

import vn.elca.training.dom.Project;
import vn.elca.training.dom.Task;

import java.util.List;

public interface TaskRepositoryCustom {
    List<Project> findProjectsByTaskName(String taskName);

    List<Task> showProjectNameOfTopTenNewTasks();
}
