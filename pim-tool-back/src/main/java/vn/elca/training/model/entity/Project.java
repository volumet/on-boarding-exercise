package vn.elca.training.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import vn.elca.training.model.enums.ProjectStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
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
    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

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
    private Set<Employee> employees = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_ID")
    private Group group;

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
        for (Employee employee : employees) {
            employee.getProjects().add(this);
        }
    }

    @PreRemove
    private void unlinkUponProjectRemove() {
        for (Employee employee : this.getEmployees()) {
            employee.getProjects().remove(this);
        }
    }

    public void clearEmployees() {
        for (Employee employee : this.employees) {
            employee.getProjects().remove(this);
        }
        this.employees.clear();
    }

    public void setGroup(Group group) {
        this.group = group;
        group.getProjects().add(this);
    }

    public Project(Long projectNumber, String name, String customer, ProjectStatus status, LocalDate st, LocalDate en) {
        this.projectNumber = projectNumber;
        this.name = name;
        this.customer = customer;
        this.status = status;
        this.startDate = st;
        this.endDate = en;
    }
}