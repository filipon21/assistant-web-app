package edu.ib.webapp.user.repository.specification;

import edu.ib.webapp.user.entity.User;
import edu.ib.webapp.user.entity.User_;
import edu.ib.webapp.user.model.dto.AssistantSearchingParamsDto;
import edu.ib.webapp.user.model.dto.UserSearchingParamsDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class UserSpecification implements Specification<User> {

    private UserSearchingParamsDto searchingParams;

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
        if (StringUtils.isNotEmpty(searchingParams.getPesel()))
            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get(User_.PESEL)),
                    "%" + searchingParams.getPesel().toUpperCase() + "%"));

        predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get(User_.ASSISTANT)),
                criteriaBuilder.isNull(root.get(User_.DOCTOR))));
        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }

}
