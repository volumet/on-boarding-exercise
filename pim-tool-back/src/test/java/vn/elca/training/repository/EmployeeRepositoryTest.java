package vn.elca.training.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import vn.elca.training.PimTestConfiguration;
import vn.elca.training.model.entity.Employee;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ActiveProfiles({"dev", "unit-test"})
@ContextConfiguration(classes = {PimTestConfiguration.class})
@RunWith(value = SpringRunner.class)
public class EmployeeRepositoryTest {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testGetListEmployee() {
        Employee employee = new Employee("VNQ", "Vinh", "Nguyen", Date.valueOf(LocalDate.now()));
        employeeRepository.save(employee);
        Assert.assertTrue(!employeeRepository.getListEmployee().isEmpty());
    }

    @Test
    public void testSearchEmployeeByVisa() {
        Employee employee = new Employee("VNQ", "Vinh", "Nguyen", Date.valueOf(LocalDate.now()));
        employeeRepository.save(employee);
        List<String> visaList = new ArrayList<>();
        visaList.add("VNQ");
        Assert.assertTrue(employeeRepository.searchEmployeeByVisa(visaList) > 0);
    }
}
