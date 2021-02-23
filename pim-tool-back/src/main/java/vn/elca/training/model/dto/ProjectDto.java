package vn.elca.training.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

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
    private String status;
    private LocalDate startDate;
}
