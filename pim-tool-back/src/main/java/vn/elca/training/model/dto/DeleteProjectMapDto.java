package vn.elca.training.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class DeleteProjectMapDto {
    @JsonProperty("project_num")
    private Map<Long, Boolean> listProjectNum;
}
