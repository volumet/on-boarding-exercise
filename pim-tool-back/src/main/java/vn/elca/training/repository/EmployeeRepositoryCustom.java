package vn.elca.training.repository;

import vn.elca.training.model.entity.Employee;

import java.util.List;

public interface EmployeeRepositoryCustom {
    Long searchEmployeeByVisa(List<String> visas);
}
