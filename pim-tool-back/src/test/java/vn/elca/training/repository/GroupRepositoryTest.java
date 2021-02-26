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
import vn.elca.training.model.entity.Group;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.time.LocalDate;

@ActiveProfiles({"dev", "unit-test"})
@ContextConfiguration(classes = {PimTestConfiguration.class})
@RunWith(value = SpringRunner.class)
public class GroupRepositoryTest {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private GroupRepository groupRepository;

    @Test
    public void testGetListGroup() {
        Employee employee = new Employee("VNQ", "Vinh", "Nguyen", Date.valueOf(LocalDate.now()));
        employeeRepository.save(employee);
        Group group = new Group();
        group.setLeader(employee);
        groupRepository.save(group);
        Assert.assertTrue(!groupRepository.getListGroup().isEmpty());
    }
}
