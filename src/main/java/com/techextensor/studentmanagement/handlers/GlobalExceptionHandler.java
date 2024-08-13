package com.techextensor.studentmanagement.handlers;

import com.techextensor.studentmanagement.utils.AppException;
import com.techextensor.studentmanagement.utils.CommonFunctions;
import com.techextensor.studentmanagement.utils.ResponseObject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleSecurityException(Exception exception) {

        final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

        switch (exception) {
            case AppException appException -> {
                // Handle application-specific exceptions
                return ResponseObject.AppExceptionResponse(appException);
            }
            case MethodArgumentNotValidException methodArgumentNotValidException -> {
                // Handle validation errors from method arguments
                Map<String, String> errors = methodArgumentNotValidException.getBindingResult().getFieldErrors().stream()
                        .collect(Collectors.toMap(
                                FieldError::getField,
                                fieldError -> CommonFunctions.getOrDefault(fieldError.getDefaultMessage(), "Validation error"),
                                (existing, replacement) -> existing
                        ));
                return ResponseObject.failure(errors, "Invalid Payload");
            }
            case ConstraintViolationException constraintViolationException -> {
                // Handle constraint violations
                Map<String, String> errorMap = new HashMap<>();
                Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();

                for (ConstraintViolation<?> violation : violations) {
                    String fieldName = violation.getPropertyPath().toString();
                    String message = violation.getMessage();
                    errorMap.put(fieldName, message);
                }

                return ResponseObject.failure(errorMap, "Invalid Payload");
            }
            case null, default -> {
                // Handle unexpected or null exceptions
                if (exception != null) {
                    logger.error(exception.getMessage(), exception);
                } else {
                    logger.error("Unexpected null exception");
                }
                // Consider storing the error in a database for further analysis
                // TODO: Implement error logging or storing logic
            }
        }

        return ResponseObject.customStatus(HttpStatus.NO_CONTENT, "Something went wrong");
    }
}