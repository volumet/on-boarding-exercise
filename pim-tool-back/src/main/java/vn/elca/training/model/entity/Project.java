package vn.elca.training.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

/**
 * @author vlp
 */
@Entity(name = "PROJECT")
@Getter
@Setter
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PROJECT_NUMBER")
    @NotNull
    private Long projectNumber;

    @Column(name = "NAME")
    @NotNull
    private String name;

    @Column(name = "CUSTOMER")
    @NotNull
    private String customer;

    @Column(name = "STATUS")
    @NotNull
    private String status;

    @Column(name = "START_DATE")
    @NotNull
    private LocalDate startDate;

    @Column(name = "END_DATE")
    private LocalDate endDate;

    @Column(name = "VERSION")
    @NotNull
    @Version
    private Long version;

    @ManyToMany(mappedBy = "projects", fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<Employee> employees;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_ID")
    Group group;

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
        for (Employee employee : employees) {
            employee.getProjects().add(this);
        }
    }

    public void setGroup(Group group) {
        this.group = group;
        group.getProjects().add(this);
    }
}