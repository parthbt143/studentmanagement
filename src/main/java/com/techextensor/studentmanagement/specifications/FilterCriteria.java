package com.techextensor.studentmanagement.specifications;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FilterCriteria {
    private String field;
    private String value;
    private FilterType filterType;

    public enum FilterType {
        EQUALS, LIKE, GREATER_THAN, GREATER_THAN_EQUAL, LESS_THAN, LESS_THAN_EQUAL
    }
}
