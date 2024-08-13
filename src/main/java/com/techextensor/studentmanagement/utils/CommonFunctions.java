package com.techextensor.studentmanagement.utils;

import java.util.Objects;

public class CommonFunctions extends Throwable {
    /**
     * Returns the provided value if it is not null; otherwise, returns the default value.
     *
     * @param value        The value to check.
     * @param defaultValue The default value to return if the provided value is null.
     * @param <T>          The type of the value and default value.
     * @return The provided value if it is not null; otherwise, the default value.
     */
    public static <T> T getOrDefault(T value, T defaultValue) {
        return (value != null) ? value : defaultValue;
    }
}
