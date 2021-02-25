package vn.elca.training.repository.impl;

import com.querydsl.jpa.impl.JPAQuery;
import vn.elca.training.model.entity.Group;
import vn.elca.training.model.entity.QEmployee;
import vn.elca.training.model.entity.QGroup;
import vn.elca.training.repository.GroupRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class GroupRepositoryImpl implements GroupRepositoryCustom {
    @PersistenceContext
    EntityManager em;


    @Override
    public List<Group> getListGroup() {
        return new JPAQuery<Group>(em)
                .from(QGroup.group)
                .join(QGroup.group.leader, QEmployee.employee)
                .fetchJoin()
                .fetch();
    }

}
