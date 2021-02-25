package vn.elca.training.repository;

import vn.elca.training.model.entity.Employee;

import java.util.List;

public interface EmployeeRepositoryCustom {
    List<Employee> getListEmployee();
    List<Employee> searchEmployeeByVisa(List<String> visas);
}
