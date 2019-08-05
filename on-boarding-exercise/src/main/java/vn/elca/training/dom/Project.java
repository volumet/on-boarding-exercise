package vn.elca.training.dom;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Project {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column
    private Date finishingDate;

	@OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
	private Set<Task> tasks = new HashSet<>();

    public Project() {
    }

    public Project(String name, Date finishingDate) {
        this.name = name;
        this.finishingDate = finishingDate;
    }

    public Project(Long id, String name, Date finishingDate) {
        this.id = id;
        this.name = name;
        this.finishingDate = finishingDate;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getFinishingDate() {
        return finishingDate;
    }

	public Set<Task> getTasks() {
		return tasks;
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}
}