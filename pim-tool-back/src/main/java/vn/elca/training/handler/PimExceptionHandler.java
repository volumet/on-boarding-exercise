package vn.elca.training.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import vn.elca.training.model.dto.InternalErrorResponse;
import vn.elca.training.model.dto.PimErrorResponse;
import vn.elca.training.model.error.ProjectServiceErrorMessage;
import vn.elca.training.model.exception.PimBusinessException;

@ControllerAdvice
public class PimExceptionHandler extends ResponseEntityExceptionHandler {
    private final Log logger = LogFactory.getLog(getClass());

    @ExceptionHandler(PimBusinessException.class)
    public final ResponseEntity<PimErrorResponse> handlePimBusinessException(PimBusinessException exception) {
        logger.error("Error occurred", exception);
        return new ResponseEntity<>(PimErrorResponse.builder()
                .errorCode(exception.getErrorMessageData().getErrorCode())
                .errorMessageKey(exception.getErrorMessageData().getErrorMessageKey())
                .parameters(exception.getParameters())
                .build(),
                exception.getErrorMessageData().getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<InternalErrorResponse> handlePimBusinessException(Exception exception) {
        logger.error("Error occurred", exception);
        return new ResponseEntity<>(InternalErrorResponse.builder()
                .errorMessageKey(exception.getMessage())
                .build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public final ResponseEntity<PimErrorResponse> handlePimBusinessException(ObjectOptimisticLockingFailureException exception) {
        logger.error("Error occurred", exception);
        return new ResponseEntity<>(PimErrorResponse.builder()
                .errorCode(ProjectServiceErrorMessage.VERSION_MUST_BE_NEWEST.getErrorCode())
                .errorMessageKey(ProjectServiceErrorMessage.VERSION_MUST_BE_NEWEST.getErrorMessageKey())
                .parameters(null)
                .build(),
                HttpStatus.BAD_REQUEST);
    }

}
