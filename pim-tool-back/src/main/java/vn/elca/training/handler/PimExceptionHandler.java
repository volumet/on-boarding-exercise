package vn.elca.training.handler;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import vn.elca.training.model.dto.PimErrorResponse;
import vn.elca.training.model.exception.PimBusinessException;

@ControllerAdvice
public class PimExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleSQLServerException(DataIntegrityViolationException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(PimBusinessException.class)
    public final ResponseEntity<PimErrorResponse> handlePimBusinessException(PimBusinessException exception) {
        return new ResponseEntity<>(PimErrorResponse.builder()
                .errorCode(exception.getErrorMessageData().getErrorCode())
                .errorMessageKey(exception.getErrorMessageData().getErrorMessageKey())
                .parameters(exception.getParameters())
                .build(),
                exception.getErrorMessageData().getStatusCode());
    }


}
