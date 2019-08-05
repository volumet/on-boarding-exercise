/*
 * TaskService
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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.jpa.impl.JPAQuery;

import vn.elca.training.dao.ITaskRepository;
import vn.elca.training.dom.Project;
import vn.elca.training.dom.QProject;
import vn.elca.training.dom.QTask;
import vn.elca.training.dom.Task;
import vn.elca.training.dom.TaskAudit.AuditType;
import vn.elca.training.dom.TaskAudit.Status;
import vn.elca.training.exception.DeadlineGreaterThanProjectFinishingDateException;

/**
 * @author vlp
 *
 */
@Service
@Transactional
public class TaskService implements ITaskService {
    private Log logger = LogFactory.getLog(getClass());

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private ITaskRepository taskRepository;
    @Autowired
    private IAuditService auditService;

	@Override
	public List<Project> findProjectsByTaskName(String taskName) {
		// @formatter:off
		return new JPAQuery(entityManager)
			.from(QTask.task)
			.innerJoin(QTask.task.project, QProject.project)
			.where(QTask.task.name.eq(taskName))
			.list(QProject.project);
		// @formatter:on
	}

	@Override
    public List<String> listNumberOfTasks(List<Project> projects) {
        List<String> result = new ArrayList<>(projects.size());
        for (Project project : projects) {
            result.add(String.format("Project %s has %s tasks.", project.getName(), project.getTasks().size()));
        }
        return result;
    }

    @Override
	public Set<Object> showProjectNameOfTopTenNewTasks() {
		Set<Object> projectNames = new HashSet<>();
		List<Task> tasks = new JPAQuery(entityManager).from(QTask.task).orderBy(QTask.task.id.desc()).limit(10)
				.list(QTask.task);
		for (Task task : tasks) {
			projectNames.add(task.getProject().getName());
		}
		return projectNames;
	}

	@Override
	public void updateDeadline(Long taskId, Date deadline) throws DeadlineGreaterThanProjectFinishingDateException {
		Task task = new JPAQuery(entityManager).from(QTask.task).where(QTask.task.id.eq(taskId))
			.singleResult(QTask.task);
		task.setDeadline(deadline);
		task.validateDeadline();
		taskRepository.save(task);
	}

    @Override
    public void createTaskForProject(String taskName, Date deadline, Project project)
        throws DeadlineGreaterThanProjectFinishingDateException {
        Task task = new Task(project, taskName);
        task.setDeadline(deadline);
		AuditType auditType = AuditType.INSERT;
		try {
		    task = taskRepository.saveAndFlush(task);
		    auditService.saveAuditDataForTask(task, auditType, Status.SUCCESS, "Task was saved successfully.");
		} catch (Exception e) {
		    String errorMessage = String.format("An exception (Error-ID = %s) happened when saving/updating task: %s",
		        UUID.randomUUID().toString(), e.getMessage());
		    logger.error(errorMessage, e);
		    auditService.saveAuditDataForTask(task, auditType, Status.FAILED, errorMessage);
		}
	}
}
