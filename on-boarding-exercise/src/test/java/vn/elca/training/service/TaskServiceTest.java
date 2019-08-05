package vn.elca.training.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.mysema.query.jpa.impl.JPAQuery;

import vn.elca.training.ApplicationLauncher;
import vn.elca.training.dao.IProjectRepository;
import vn.elca.training.dao.ITaskRepository;
import vn.elca.training.dom.Project;
import vn.elca.training.dom.QTask;
import vn.elca.training.dom.QTaskAudit;
import vn.elca.training.dom.Task;
import vn.elca.training.dom.TaskAudit.AuditType;
import vn.elca.training.exception.DeadlineGreaterThanProjectFinishingDateException;

/**
 * @author vlp
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationLauncher.class)
@TransactionConfiguration
// please remove this annotation to do the Hibernate exercise
@Ignore
public class TaskServiceTest {
	@PersistenceContext
	private EntityManager em;
	@Autowired
	private IProjectRepository projectRepository;
	@Autowired
	private ITaskRepository taskRepository;
	@Autowired
	private ITaskService taskService;

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
        Assert.assertTrue(taskService.showProjectNameOfTopTenNewTasks().size() > 0);
	}

    @SuppressWarnings("deprecation")
    @Test
    public void testUpdateDeadline() {
		createProjectAndTaskData(1, 5);
		Task task = new JPAQuery(em).from(QTask.task).limit(1).singleResult(QTask.task);
		try {
            // test update the deadline with a new invalid deadline
            Date newDeadline = new Date(task.getDeadline().getYear() + 2,
                task.getDeadline().getMonth(), task.getDeadline().getDate());
            taskService.updateDeadline(task.getId(), newDeadline);
        } catch (DeadlineGreaterThanProjectFinishingDateException e) {
            em.clear();
			Task task1 = new JPAQuery(em).from(QTask.task).where(QTask.task.id.eq(task.getId()))
				.singleResult(QTask.task);
			Assert.assertEquals("Deadline should not be changed here", task.getDeadline(), task1.getDeadline());
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
            new JPAQuery(em).from(QTask.task).where(QTask.task.name.eq("Task test CreateTaskFromProject"))
                .singleResult(QTask.task));
        Assert.assertNotNull("Task audit data should be saved into DB for admin to trace back later.",
            new JPAQuery(em).from(QTaskAudit.taskAudit)
                .where(QTaskAudit.taskAudit.taskName.eq("Task test CreateTaskFromProject")
                    .and(QTaskAudit.taskAudit.auditType.eq(AuditType.INSERT)))
            .singleResult(QTaskAudit.taskAudit));
        Assert.assertNull(
            "Task audit data for the update case should not be saved because we threw exception before it.",
            new JPAQuery(em).from(QTaskAudit.taskAudit)
                .where(QTaskAudit.taskAudit.taskName.eq("Task test CreateTaskFromProject")
                .and(QTaskAudit.taskAudit.auditType.eq(AuditType.UPDATE)))
            .singleResult(QTaskAudit.taskAudit));
	}
}
