package com.techextensor.studentmanagement.utils;

import java.util.Objects;

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

}
