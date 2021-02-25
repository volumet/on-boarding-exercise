package vn.elca.training.repository.impl;

import com.querydsl.jpa.impl.JPAQuery;
import vn.elca.training.model.entity.Employee;
import vn.elca.training.model.entity.QEmployee;
import vn.elca.training.repository.EmployeeRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepositoryImpl implements EmployeeRepositoryCustom {
    @PersistenceContext
    EntityManager em;

    @Override
    public List<Employee> getListEmployee() {
        return new JPAQuery<Employee>(em)
                .from(QEmployee.employee)
                .fetch();
    }

    @Override
    public List<Employee> searchEmployeeByVisa(List<String> visas) {
//        List<Employee> employees = new ArrayList<>();
//        for (String visa : visas) {
            return new JPAQuery<Employee>(em)
                    .from(QEmployee.employee)
                    .where(QEmployee.employee.visa.in(visas))
                    .fetch();
//            if (!searchedList.isEmpty()) {
//                employees.add(searchedList.get(0));
//            }
//        }

    }
}
