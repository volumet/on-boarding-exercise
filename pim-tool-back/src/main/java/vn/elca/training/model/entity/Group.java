package vn.elca.training.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity(name = "GROUP")
@Getter
@Setter
@NoArgsConstructor
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "VERSION")
    @NotNull
    private Long version;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_LEADER_ID")
    private Employee leader;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private Set<Project> projects;
}
