package vn.elca.training.service;

import java.time.LocalDate;
import java.util.List;

import vn.elca.training.model.entity.Project;
import vn.elca.training.model.exception.DeadlineGreaterThanProjectFinishingDateException;

/**
 * @author vlp
 *
 */
public interface TaskService {
	List<Project> findProjectsByTaskName(String taskName);

    List<String> listNumberOfTasks(List<Project> projects);

    List<String> listProjectNameOfRecentTasks();

	void updateDeadline(Long taskId, LocalDate deadline) throws DeadlineGreaterThanProjectFinishingDateException;

    void createTaskForProject(String taskName, LocalDate deadline, Project project);
}
