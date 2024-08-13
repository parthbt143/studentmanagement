package com.techextensor.studentmanagement.utils;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
public class ResponseObject<T> {
    //    200 if it has data
    //    204 if no data
    public int code;
    //    message
    public String message;
    //    Total count
    public long count;
    //    Actual Data
    public T data;

    public ResponseObject(@NonNull HttpStatusCode code, String message, long count, T data) {
        this.code = code.value();
        this.message = message;
        if (data instanceof Page) {
            this.data = (T) ((Page<?>) data).getContent();
        } else {
            this.data = data;
        }
        this.count = count;
    }

    // Static methods for creating common response types
    public static <T> ResponseEntity<ResponseObject<T>> success(T data) {
        return success(data, "Success");
    }

    // Static methods for creating common response types
    public static <T> ResponseEntity<ResponseObject<T>> success(String message) {
        return new ResponseEntity<>(new ResponseObject<>(HttpStatus.OK, message, 0, null),
                HttpStatus.OK);
    }

    // Static methods for creating common response types
    public static <T> ResponseEntity<ResponseObject<T>> success(T data, String successMessage, String failureMessage) {
        long count = calculateCount(data);
        return success(data, count > 0 ? successMessage : failureMessage);
    }

    public static <T> ResponseEntity<ResponseObject<T>> success(Optional<T> data, String successMessage, String failureMessage) {
        return data.map(t -> new ResponseEntity<>(new ResponseObject<>(HttpStatus.OK, successMessage, 1, t), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(new ResponseObject<>(HttpStatus.NO_CONTENT, failureMessage, 0, null), HttpStatus.OK));
    }

    public static <T> ResponseEntity<ResponseObject<T>> success(T data, String message) {
        long count = calculateCount(data);
        return new ResponseEntity<>(new ResponseObject<>(count > 0 ? HttpStatus.OK : HttpStatus.NO_CONTENT, message, count, data),
                HttpStatus.OK);
    }

    public static <T> ResponseEntity<ResponseObject<T>> created(T data, String message) {
        return new ResponseEntity<>(new ResponseObject<>(HttpStatus.CREATED, message, 1, data),
                HttpStatus.OK);
    }

    public static <T> ResponseEntity<ResponseObject<T>> updated(T data, String message) {
        return new ResponseEntity<>(new ResponseObject<>(HttpStatus.ACCEPTED, message, 1, data),
                HttpStatus.OK);
    }


    public static <T> ResponseEntity<ResponseObject<T>> failure(T data, String message) {
        return new ResponseEntity<>(new ResponseObject<>(HttpStatus.NO_CONTENT, message, 0, data),
                HttpStatus.OK);
    }

    public static <T> ResponseEntity<ResponseObject<T>> failure(String message) {
        return new ResponseEntity<>(new ResponseObject<>(HttpStatus.NO_CONTENT, message, 0, null),
                HttpStatus.OK);
    }

    public static <T> ResponseEntity<ResponseObject<T>> AppExceptionResponse(AppException appException) {
        return new ResponseEntity<>(new ResponseObject<>(CommonFunctions.getOrDefault(appException.getStatus(), HttpStatus.NO_CONTENT), appException.getErrorMessage(), 0, null), HttpStatus.OK);
    }

    public static <T> ResponseEntity<ResponseObject<T>> UnAuthorized(String message) {
        return new ResponseEntity<>(new ResponseObject<>(HttpStatus.UNAUTHORIZED, message, 0, null), HttpStatus.UNAUTHORIZED);
    }

    public static <T> ResponseEntity<ResponseObject<T>> customStatus(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new ResponseObject<>(httpStatus, message, 0, null), httpStatus);
    }


    // Helper method to calculate count based on data type
    private static <T> long calculateCount(T data) {
        if (data instanceof Page) {
            return ((Page<?>) data).getTotalElements();
        } else if (data instanceof Iterable) {
            return ((Iterable<?>) data).spliterator().estimateSize();
        } else if (Objects.nonNull(data)) {
            return 1;
        }
        return 0;
    }
}
