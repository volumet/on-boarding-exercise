/*
 * ITaskService
 * 
 * Project: KStA ZHQUEST
 *
 * Copyright 2014 by ELCA Informatik AG
 * Steinstrasse 21, CH-8036 Zurich
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of ELCA Informatik AG ("Confidential Information"). You 
 * shall not disclose such "Confidential Information" and shall
 * use it only in accordance with the terms of the license
 * agreement you entered into with ELCA.
 */

package vn.elca.training.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import vn.elca.training.dom.Project;
import vn.elca.training.exception.DeadlineGreaterThanProjectFinishingDateException;

/**
 * @author vlp
 *
 */
public interface ITaskService {
	List<Project> findProjectsByTaskName(String taskName);

    List<String> listNumberOfTasks(List<Project> projects);

    Set<Object> showProjectNameOfTopTenNewTasks();

	void updateDeadline(Long taskId, Date deadline) throws DeadlineGreaterThanProjectFinishingDateException;

    void createTaskForProject(String taskName, Date deadline, Project project) throws DeadlineGreaterThanProjectFinishingDateException;

}
