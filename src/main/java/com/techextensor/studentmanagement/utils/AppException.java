package com.techextensor.studentmanagement.utils;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public class AppException extends RuntimeException {

    String errorMessage;
    HttpStatusCode status;

    public AppException(String errorMessage) {
        this.errorMessage = errorMessage;
    }



}
