package com.techextensor.studentmanagement.specifications;


import com.techextensor.studentmanagement.utils.CommonFunctions;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class SpecificationBuilder<T> {

    public Specification<T> build(List<FilterCriteria> filters, Pageable pageable) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filters != null && !filters.isEmpty()) {
                for (FilterCriteria filter : filters) {
                    String field = filter.getField();
                    String value = filter.getValue();
                    if (StringUtils.hasText(value)) {
                        Predicate predicate = buildPredicate(root, criteriaBuilder, field, value, CommonFunctions.getOrDefault(filter.getFilterType(), FilterCriteria.FilterType.EQUALS));
                        if (predicate != null) {
                            predicates.add(predicate);
                        }
                    }
                }
            }
            // Apply sorting from pageable
            if (pageable != null) {
                pageable.getSort();
                List<Order> orders = new ArrayList<>();
                pageable.getSort().forEach(order -> {
                    if (order.isAscending()) {
                        orders.add(criteriaBuilder.asc(root.get(order.getProperty())));
                    } else {
                        orders.add(criteriaBuilder.desc(root.get(order.getProperty())));
                    }
                });
                query.orderBy(orders);
            }
            // Apply predicates and sorting
            query.where(predicates.toArray(new Predicate[0]));

            return query.getRestriction();
        };
    }

    private Predicate buildPredicate(Root<T> root, CriteriaBuilder criteriaBuilder, String field, String value, FilterCriteria.FilterType filterType) {
        Path<?> path = root.get(field);
        return switch (filterType) {
            // EQUALS is set as default
            case EQUALS -> criteriaBuilder.equal(path, convertValue(path.getJavaType(), value));
            case GREATER_THAN -> criteriaBuilder.greaterThan(root.get(field), value);
            case LESS_THAN -> criteriaBuilder.lessThan(root.get(field), value);
            case LIKE -> criteriaBuilder.like(criteriaBuilder.lower(root.get(field)), "%" + value.toLowerCase() + "%");
            case GREATER_THAN_EQUAL -> criteriaBuilder.greaterThanOrEqualTo(root.get(field), value);
            case LESS_THAN_EQUAL -> criteriaBuilder.lessThanOrEqualTo(root.get(field), value);

        };
    }

    // Utility method to convert the value based on the field type
    private <X> X convertValue(Class<X> type, String value) {
        if (type == Integer.class) {
            return type.cast(Integer.valueOf(value));
        } else if (type == Double.class) {
            return type.cast(Double.valueOf(value));
        } else if (type == Long.class) {
            return type.cast(Long.valueOf(value));
        }
        // Add more conversions as needed
        return type.cast(value);
    }
}
