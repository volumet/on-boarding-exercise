package vn.elca.training.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity(name = "EMPLOYEE")
@Getter
@Setter
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "VISA")
    @NotNull
    private String visa;

    @Column(name = "FIRST_NAME")
    @NotNull
    private String firstName;

    @Column(name = "LAST_NAME")
    @NotNull
    private String lastName;

    @Column(name = "BIRTH_DATE")
    @NotNull
    private Date birthdate;

    @Column(name = "VERSION")
    @NotNull
    private Long version;

    @OneToOne(mappedBy = "leader", fetch = FetchType.LAZY)
    private Group group;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "PROJECT_EMPLOYEE",
            joinColumns = @JoinColumn(name = "EMPLOYEE_ID"),
            inverseJoinColumns = @JoinColumn(name = "PROJECT_ID")
    )
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<Project> projects;
}
