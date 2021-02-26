package vn.elca.training.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.elca.training.model.dto.EmployeeDto;
import vn.elca.training.service.EmployeeService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
@CrossOrigin
public class EmployeeController extends AbstractApplicationController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping("/load")
    public List<EmployeeDto> loadEmployee() {
        return employeeService.getListEmployee()
                .stream()
                .map(mapper::employeeToEmployeeDto)
                .collect(Collectors.toList());
    }
}
