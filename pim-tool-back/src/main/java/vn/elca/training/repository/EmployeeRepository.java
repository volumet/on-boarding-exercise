package vn.elca.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import vn.elca.training.model.entity.Employee;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, QuerydslPredicateExecutor<Long>, EmployeeRepositoryCustom {

    List<Employee> findEmployeeByVisaIn(List<String> visas);

}
