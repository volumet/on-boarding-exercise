package vn.elca.training.model.exception;

import lombok.Getter;
import lombok.Setter;
import vn.elca.training.model.error.PimErrorMessageInterface;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PimBusinessException extends RuntimeException {
    private PimErrorMessageInterface errorMessageData;
    private List<String> parameters = new ArrayList<>();

    public PimBusinessException(PimErrorMessageInterface errorMessage) {
        this.errorMessageData = errorMessage;
    }

    public PimBusinessException(PimErrorMessageInterface errorMessage, List<String> parameters) {
        this.errorMessageData = errorMessage;
        this.parameters = parameters;
    }
}
