package vn.elca.training.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PimErrorResponse {
    private String errorCode;
    private String errorMessageKey;
    private List<String> parameters;
}
