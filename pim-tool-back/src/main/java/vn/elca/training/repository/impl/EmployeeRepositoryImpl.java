package vn.elca.training.repository.impl;

import com.querydsl.jpa.impl.JPAQuery;
import vn.elca.training.model.entity.Employee;
import vn.elca.training.model.entity.QEmployee;
import vn.elca.training.repository.EmployeeRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class EmployeeRepositoryImpl implements EmployeeRepositoryCustom {
    @PersistenceContext
    EntityManager em;

    @Override
    public Long searchEmployeeByVisa(List<String> visas) {
            return new JPAQuery<Employee>(em)
                    .from(QEmployee.employee)
                    .where(QEmployee.employee.visa.in(visas))
                    .fetchCount();

    }
}
