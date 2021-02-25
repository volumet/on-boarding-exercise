package vn.elca.training.model.validator;

import org.springframework.stereotype.Component;
import vn.elca.training.model.error.PimErrorMessageInterface;
import vn.elca.training.model.exception.PimBusinessException;

import java.util.List;

@Component
public class PimValidator {

    public void validateTrue(boolean condition, PimErrorMessageInterface errorMessage) {
        validateTrue(condition, errorMessage, null);
    }

    public void validateTrue(boolean condition, PimErrorMessageInterface errorMessage, List<String> parameters) {
        if (!condition) {
            throw new PimBusinessException(errorMessage, parameters);
        }
    }

    public void validateFalse(boolean condition, PimErrorMessageInterface errorMessage) {
        validateFalse(condition, errorMessage, null);
    }

    public void validateFalse(boolean condition, PimErrorMessageInterface errorMessage, List<String> parameters) {
        if (condition) {
            throw new PimBusinessException(errorMessage, parameters);
        }
    }

    public void validateNull(Object value, PimErrorMessageInterface errorMessage) {
        validateTrue(value == null, errorMessage, null);
    }

    public void validateNull(Object value, PimErrorMessageInterface errorMessage, List<String> parameters) {
        validateTrue(value == null, errorMessage, parameters);
    }

    public void validateNotNull(Object value, PimErrorMessageInterface errorMessage) {
        validateFalse(value == null, errorMessage, null);
    }

    public void validateNotNull(Object value, PimErrorMessageInterface errorMessage, List<String> parameters) {
        validateFalse(value == null, errorMessage, parameters);
    }

    public void validateEmpty(String value, PimErrorMessageInterface errorMessage) {
        validateTrue(value.isEmpty(), errorMessage, null);
    }

    public void validateEmpty(String value, PimErrorMessageInterface errorMessage, List<String> parameters) {
        validateTrue(value.isEmpty(), errorMessage, parameters);
    }

    public void validateNotEmpty(String value, PimErrorMessageInterface errorMessage) {
        validateFalse(value.isEmpty(), errorMessage, null);
    }

    public void validateNotEmpty(String value, PimErrorMessageInterface errorMessage, List<String> parameters) {
        validateFalse(value.isEmpty(), errorMessage, parameters);
    }

}
