package vn.elca.training.model.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ProjectServiceErrorMessage implements PimErrorMessageInterface {
    FIELD_MUST_NOT_BE_NULL
            ("BE-01-01",
                    "CreateProject.Error.Msg.fieldMustNotBeNull",
                    HttpStatus.BAD_REQUEST),
    DEADLINE_MUST_NOT_BE_GREATER_THAN_PROJECT_FINISHING_DATE
            ("BE-01-02",
                    "CreateProject.Error.Msg.deadlineMustNotBeGreaterThanProjectFinishingDate",
                    HttpStatus.BAD_REQUEST),
    MEMBER_MUST_BE_EXIST
            ("BE-01-03",
                    "CreateProject.Error.Msg.memberMustBeExist",
                    HttpStatus.NOT_FOUND),
    PROJECT_NUMBER_MUST_NOT_BE_DUPLICATE
            ("BE-01-04",
                    "CreateProject.Error.Msg.projectNumberMustNotBeDuplicate",
                    HttpStatus.BAD_REQUEST),
    PROJECT_NUMBER_MUST_BE_EXIST("BE-01-05",
            "CreateProject.Error.Msg.projectNumberMustBeExist",
            HttpStatus.NOT_FOUND);
    private String errorCode;
    private String errorMessageKey;
    private HttpStatus statusCode;
}
