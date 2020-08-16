package vn.elca.training.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.querydsl.jpa.impl.JPAQuery;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import org.springframework.test.context.junit4.SpringRunner;
import vn.elca.training.ApplicationWebConfig;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.entity.QTask;
import vn.elca.training.model.entity.QTaskAudit;
import vn.elca.training.model.entity.Task;
import vn.elca.training.model.entity.TaskAudit;
import vn.elca.training.model.exception.DeadlineGreaterThanProjectFinishingDateException;
import vn.elca.training.repository.ProjectRepository;
import vn.elca.training.repository.TaskRepository;

/**
 * @author vlp
 *
 */
@ContextConfiguration(classes = {ApplicationWebConfig.class})
@RunWith(value=SpringRunner.class)
// please remove this annotation to do the Hibernate exercise
@Ignore
public class TaskServiceTest {
	@PersistenceContext
	private EntityManager em;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private TaskService taskService;

	private void createProjectAndTaskData(int nbProjects, int nbTasks) {
		// create 'nbProjects' Projects, each project has 'nbTasks' tasks.
		for (int i = 1; i <= nbProjects; i++) {
			Date finishingDate = new Date();
			finishingDate = new Date(finishingDate.getYear() + 1, finishingDate.getMonth(), finishingDate.getDate());
			Project project = new Project(String.format("Project %s", i), finishingDate);
			project = projectRepository.save(project);
			for (int j = 0; j < nbTasks; j++) {
				taskRepository.save(new Task(project, String.format("Task %s", j)));
			}
		}
	}

	@Test
	public void testListNumberOfTasks() {
		createProjectAndTaskData(1, 3);
		List<Project> projectsByTaskName = taskService.findProjectsByTaskName("Task 1");
		Assert.assertTrue(taskService.listNumberOfTasks(projectsByTaskName).size() > 0);
	}

	@Test
	public void testShowProjectNameOfTopTenNewTasks() {
		createProjectAndTaskData(100, 1);
		System.out.println(">>>>>>> Start Test case >>>>>");
		Set<Object> names = taskService.showProjectNameOfTopTenNewTasks();
		Assert.assertTrue(names.size() > 0);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testUpdateDeadline() {
		createProjectAndTaskData(1, 5);
		final Long projectId = 1L;
		final Task task = taskRepository.findOne(projectId);
		final Date finishingDate = task.getDeadline();
		try {
			// test update the deadline with a new invalid deadline
			Date newDeadline = new Date(task.getDeadline().getYear() + 2,
					task.getDeadline().getMonth(), task.getDeadline().getDate());
			taskService.updateDeadline(projectId, newDeadline);
		} catch (DeadlineGreaterThanProjectFinishingDateException e) {
			em.clear();
			Task task1 = taskRepository.findOne(projectId);
			Assert.assertEquals("Deadline should not be changed here", finishingDate, task1.getDeadline());
		}
	}

	@Test
	public void testCreateTaskForProject() {
		try {
			Date curDate = new Date();
			@SuppressWarnings("deprecation")
			Date dateBefore = new Date(curDate.getYear(), curDate.getMonth(), curDate.getDate());
			Project project = projectRepository.saveAndFlush(new Project("Project 1", dateBefore));
			taskService.createTaskForProject("Task test CreateTaskFromProject", new Date(), project);
		} catch (Exception e) {
			// just swallow it here because we are testing the case the task is inserted with invalid deadline.
		}
		Assert.assertNull("Task should not be saved to DB because its deadline is invalid.",
				new JPAQuery<Task>(em)
						.from(QTask.task)
						.where(QTask.task.name.eq("Task test CreateTaskFromProject"))
						.fetchFirst());
		Assert.assertNotNull("Task audit data should be saved into DB for admin to trace back later.",
				new JPAQuery<Task>(em)
						.from(QTaskAudit.taskAudit)
						.where(QTaskAudit.taskAudit.taskName.eq("Task test CreateTaskFromProject")
								.and(QTaskAudit.taskAudit.auditType.eq(TaskAudit.AuditType.INSERT)))
						.fetchFirst());
		Assert.assertNull(
				"Task audit data for the update case should not be saved because we threw exception before it.",
				new JPAQuery<Task>(em)
						.from(QTaskAudit.taskAudit)
						.where(QTaskAudit.taskAudit.taskName.eq("Task test CreateTaskFromProject")
								.and(QTaskAudit.taskAudit.auditType.eq(TaskAudit.AuditType.UPDATE)))
						.fetchFirst());
	}
}
