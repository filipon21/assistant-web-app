package edu.ib.webapp.user.repository.specification;

import edu.ib.webapp.user.model.dto.AssistantSearchingParamsDto;
import edu.ib.webapp.user.entity.User;
import edu.ib.webapp.user.entity.User_;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Data
@AllArgsConstructor
public class AssistantSpecification implements Specification<User> {

    private AssistantSearchingParamsDto searchingParams;

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.isNotEmpty(searchingParams.getUserFirstName()))
            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get(User_.USER_FIRST_NAME)),
                    "%" + searchingParams.getUserFirstName().toUpperCase() + "%"));
        if (StringUtils.isNotEmpty(searchingParams.getUserLastName()))
            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get(User_.USER_LAST_NAME)),
                    "%" + searchingParams.getUserLastName().toUpperCase() + "%"));
        if (StringUtils.isNotEmpty(searchingParams.getPhoneNumber()))
            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get(User_.PHONE_NUMBER)),
                    "%" + searchingParams.getPhoneNumber().toUpperCase() + "%"));
        if (Objects.nonNull(searchingParams.getIsOnline()))
            predicates.add(criteriaBuilder.equal(root.get(User_.IS_ONLINE),
                    searchingParams.getIsOnline()));
        predicates.add(criteriaBuilder.isNotNull(root.get(User_.ASSISTANT)));
        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
