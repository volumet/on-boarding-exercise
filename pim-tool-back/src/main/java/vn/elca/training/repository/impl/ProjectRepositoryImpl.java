package vn.elca.training.repository.impl;

import com.querydsl.jpa.impl.JPAQuery;
import vn.elca.training.model.entity.*;
import vn.elca.training.repository.ProjectRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class ProjectRepositoryImpl implements ProjectRepositoryCustom {
    @PersistenceContext
    EntityManager em;

    @Override
    public List<Project> getListProject() {
        return new JPAQuery<Project>(em)
                .from(QProject.project)
                .fetch();
    }

    @Override
    public List<Project> getProjectByNumber(Long proNum) {
        return new JPAQuery<Project>(em)
                .from(QProject.project)
                .join(QProject.project.group, QGroup.group)
                .fetchJoin()
                .join(QProject.project.employees, QEmployee.employee)
                .fetchJoin()
                .where(QProject.project.projectNumber.eq(proNum))
                .fetch();
    }

    @Override
    public void deleteOneProject(Project project) {
        em.remove(project);
        for (Employee employee : project.getEmployees()) {
            employee.getProjects().remove(project);
        }
    }

    @Override
    public Project getProjectBySingleNumber(Long proNum) {
        return new JPAQuery<Project>(em)
                .from(QProject.project)
                .join(QProject.project.group, QGroup.group)
                .fetchJoin()
                .join(QProject.project.employees, QEmployee.employee)
                .fetchJoin()
                .where(QProject.project.projectNumber.eq(proNum)
                        .and(QProject.project.status.eq("NEW")))
                .fetchOne();
    }


}
