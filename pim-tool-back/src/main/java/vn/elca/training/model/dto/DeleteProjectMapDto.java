package vn.elca.training.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DeleteProjectMapDto {
    @JsonProperty("project_num")
    private List<Long> listProjectNum = new ArrayList<>();
}
