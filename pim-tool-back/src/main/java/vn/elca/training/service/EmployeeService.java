package vn.elca.training.service;

import org.springframework.stereotype.Service;
import vn.elca.training.model.entity.Employee;

import java.util.List;

@Service
public interface EmployeeService {
    List<Employee> getListEmployee();
}
