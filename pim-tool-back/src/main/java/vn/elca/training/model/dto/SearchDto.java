package vn.elca.training.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import vn.elca.training.model.enums.ProjectStatus;

@Getter
@Setter
@AllArgsConstructor
public class SearchDto {
    @JsonProperty("search_value")
    private String searchValue;

    @JsonProperty("status_value")
    private ProjectStatus statusValue;
}
