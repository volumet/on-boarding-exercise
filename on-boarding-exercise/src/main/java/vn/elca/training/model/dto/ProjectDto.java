package vn.elca.training.model.dto;

import java.util.Date;

/**
 * @author gtn
 *
 */
public class ProjectDto {
    private Long id;
    private String name;
    private Date finishingDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getFinishingDate() {
        return finishingDate;
    }

    public void setFinishingDate(Date finishingDate) {
        this.finishingDate = finishingDate;
    }
}
