package vn.elca.training.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InternalErrorResponse {
    private String errorMessageKey;
}
