package vn.elca.training.dao.custom;

import com.mysema.query.jpa.impl.JPAQuery;
import vn.elca.training.dom.Project;
import vn.elca.training.dom.QProject;
import vn.elca.training.dom.QTask;
import vn.elca.training.dom.Task;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class TaskRepositoryImpl implements TaskRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Project> findProjectsByTaskName(String taskName) {
        return new JPAQuery(entityManager)
                .from(QTask.task)
                .innerJoin(QTask.task.project, QProject.project)
                .where(QTask.task.name.eq(taskName))
                .list(QProject.project);
    }

    @Override
    public List<Task> showProjectNameOfTopTenNewTasks() {
        return new JPAQuery(entityManager)
                .from(QTask.task)
                .orderBy(QTask.task.id.desc())
                .limit(10)
                .list(QTask.task);
    }
}
