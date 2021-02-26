package vn.elca.training.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ProjectReqDto {
    @JsonProperty("project_num")
    private Long projectNumber;

    @JsonProperty("project_name")
    private String projectName;

    @JsonProperty("customer")
    private String customer;

    @JsonProperty("group")
    private Long groupId;

    @JsonProperty("member")
    private List<String> employeeVisa;

    @JsonProperty("status")
    private String status;

    @JsonProperty("start_date")
    private LocalDate startDate;

    @JsonProperty("end_date")
    private LocalDate endDate;
}