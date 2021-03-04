package vn.elca.training.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import vn.elca.training.model.dto.SearchDto;
import vn.elca.training.model.entity.*;
import vn.elca.training.model.enums.ProjectStatus;
import vn.elca.training.repository.ProjectRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public class ProjectRepositoryImpl implements ProjectRepositoryCustom {
    @PersistenceContext
    EntityManager em;

    @Override
    public Optional<Project> getProjectByNumber(Long proNum) {
        return Optional.ofNullable(new JPAQuery<Project>(em)
                .from(QProject.project)
                .join(QProject.project.group, QGroup.group)
                .fetchJoin()
                .leftJoin(QProject.project.employees, QEmployee.employee)
                .fetchJoin()
                .where(QProject.project.projectNumber.eq(proNum))
                .distinct()
                .fetchOne());
    }

    @Override
    public Long checkVersion(Long proNum) {
        return new JPAQuery<Project>(em)
                .select(QProject.project.version)
                .from(QProject.project)
                .where(QProject.project.projectNumber.eq(proNum))
                .fetchOne();
    }

    @Override
    public Long checkProjectNumberExist(Long proNum) {
        return new JPAQuery<Project>(em)
                .from(QProject.project)
                .where(QProject.project.projectNumber.eq(proNum))
                .distinct()
                .fetchCount();
    }

    @Override
    public Project getNewProjectByNumber(Long proNum) {
        return new JPAQuery<Project>(em)
                .from(QProject.project)
                .join(QProject.project.group, QGroup.group)
                .fetchJoin()
                .leftJoin(QProject.project.employees, QEmployee.employee)
                .fetchJoin()
                .where(QProject.project.projectNumber.eq(proNum)
                        .and(QProject.project.status.eq(ProjectStatus.NEW)))
                .fetchOne();
    }

    @Override
    public List<Project> searchProject(SearchDto searchDto) {
        BooleanExpression condition = QProject.project.projectNumber.stringValue().contains(searchDto.getSearchValue())
                .or(QProject.project.customer.contains(searchDto.getSearchValue()))
                .or(QProject.project.name.contains(searchDto.getSearchValue()));
        //Status != ALL
        if(searchDto.getStatusValue()  != ProjectStatus.ALL){
            condition = condition.and(QProject.project.status.eq(searchDto.getStatusValue()));
        }
        return new JPAQuery<Project>(em)
                .from(QProject.project)
                .join(QProject.project.group, QGroup.group)
                .fetchJoin()
                .leftJoin(QProject.project.employees, QEmployee.employee)
                .fetchJoin()
                .where(condition)
                .distinct()
                .fetch();
    }
}
