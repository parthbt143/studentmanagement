package com.techextensor.studentmanagement.utils;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Validators extends Throwable {

    public static void StringIsNullOrEmpty(String string, String validationMessage) throws Exception {
        if (Objects.isNull(string) || string.trim().isEmpty()) {
            throw new AppException(validationMessage);
        }
    }


    public static void StringIsRequired(String string, String key) throws Exception {
        StringIsNullOrEmpty(string, key + " is required");
    }

    public static void ObjectIsNull(Object object, String validationMessage) throws Exception {
        if (Objects.isNull(object)) {
            throw new AppException(validationMessage);
        }
    }

    public static void ObjectIsRequired(Object object, String key) throws Exception {
        ObjectIsNull(object, key + " is required");
    }

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * Validates an object using Jakarta Bean Validation.
     *
     * @param <T>    the type of the object to validate
     * @param object the object to validate
     * @throws ConstraintViolationException if validation fails
     */
    public static <T> void validateObject(T object) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            String message = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(", "));
            throw new ConstraintViolationException(message, violations);
        }
    }
}
