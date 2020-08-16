package vn.elca.training.repository.custom;

import com.mysema.query.jpa.impl.JPAQuery;
import vn.elca.training.model.QProject;
import vn.elca.training.model.QTask;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.entity.Task;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

// Rename this class so that Spring can scan and wire this component correctly
public class RenameThisClass implements TaskRepositoryCustom {
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
