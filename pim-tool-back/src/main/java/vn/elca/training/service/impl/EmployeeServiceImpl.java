package vn.elca.training.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import vn.elca.training.model.entity.Employee;
import vn.elca.training.repository.EmployeeRepository;
import vn.elca.training.service.EmployeeService;

import java.util.List;

@Service
@Profile("dev")
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getListEmployee() {
        return employeeRepository.getListEmployee();
    }
}
