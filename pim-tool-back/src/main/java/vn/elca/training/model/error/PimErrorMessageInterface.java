package vn.elca.training.model.error;

import org.springframework.http.HttpStatus;

public interface PimErrorMessageInterface {
    String getErrorCode();
    String getErrorMessageKey();
    HttpStatus getStatusCode();
}
