package vn.elca.training.model.dto;

import lombok.Getter;
import lombok.Setter;
import vn.elca.training.model.enums.ProjectStatus;

import java.time.LocalDate;

/**
 * @author gtn
 *
 */

@Getter
@Setter
public class ProjectDto {
    private Long id;
    private Long projectNumber;
    private String name;
    private String customer;
    private ProjectStatus status;
    private LocalDate startDate;
}
