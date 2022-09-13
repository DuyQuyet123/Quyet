package quiet.com.ShopQA.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import quiet.com.ShopQA.Ultils.DataUtils;
import quiet.com.ShopQA.Ultils.EnumUtils;

import javax.persistence.criteria.Predicate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Component
public class CommonSpecification<T> implements Serializable {

    public Specification<T> withIsActive(String value) {
        Boolean boolValue = "true".equalsIgnoreCase(value) ? Boolean.TRUE :
                "false".equalsIgnoreCase(value) ? Boolean.FALSE : null;

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("isActive"), boolValue);
    }

    public Specification<T> withFilter(Map<String, String> filterColumns, List<String> searchColumns,
                                       Map<String, String> filterParam, String lang) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            filterParam.forEach((key, value) -> {
                if (value != null && value.length() > 0) {
                    if (key.equalsIgnoreCase(EnumUtils.SEARCH_KEY)) {
                        searchColumns.forEach(columnSearch -> {
                            predicates.add(criteriaBuilder.like(
                                    criteriaBuilder.lower(
                                            root.get(columnSearch)),
                                    DataUtils.buildSearch(value).toLowerCase()));
                            predicates.add(criteriaBuilder.like(
                                    criteriaBuilder.lower(
                                            criteriaBuilder.function(
                                                    "jsonb_extract_path_text",
                                                    String.class,
                                                    root.<String>get("translation"),
                                                    criteriaBuilder.literal(lang),
                                                    criteriaBuilder.literal(columnSearch))),
                                    DataUtils.buildSearch(value).toLowerCase()));
                        });
                        criteriaBuilder.or(predicates.toArray(new Predicate[0]));

                    }
                    String column = filterColumns.get(key);
                    if (column != null) {
                        Boolean isBolean = Boolean.parseBoolean(value);
                        if (isBolean) {
                            Boolean value1 = "true".equalsIgnoreCase(value) ? Boolean.TRUE :
                                    "false".equalsIgnoreCase(value) ? Boolean.FALSE : null;
                            predicates.add(criteriaBuilder.equal(root.get(column), value1));

                        } else {
                            predicates.add(criteriaBuilder.equal(root.get(column), value));
                            // Filter in translation
                            predicates.add(criteriaBuilder.equal(
                                    criteriaBuilder.function(
                                            "jsonb_extract_path_text",
                                            String.class,
                                            root.<String>get("translation"),
                                            criteriaBuilder.literal(lang),
                                            criteriaBuilder.literal(column)), value));
                        }
                    }
                }
            });
            if (predicates.size() <= 0) {
                return null;
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }
}
