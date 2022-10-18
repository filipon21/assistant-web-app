package edu.ib.webapp.user.repository.specification;

import edu.ib.webapp.user.entity.Visit;
import edu.ib.webapp.user.entity.User;
import edu.ib.webapp.user.entity.User_;
import edu.ib.webapp.user.entity.Visit_;
import edu.ib.webapp.user.enums.VisitStatusEnum;
import edu.ib.webapp.user.model.dto.VisitSearchingParamsDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
public class VisitSpecification implements Specification<Visit> {

    private VisitSearchingParamsDto searchingParams;

    @Override
    public Predicate toPredicate(Root<Visit> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        Join<Visit, User> joinUser = root.join(Visit_.users);
        criteriaQuery.distinct(true);

        if (Objects.nonNull(searchingParams.getVisitStatusEnum()))
            predicates.add(criteriaBuilder.equal(root.get(Visit_.VISIT_STATUS_ENUM),
                    searchingParams.getVisitStatusEnum()));

        if (Objects.nonNull(searchingParams.getVisitTypeEnum()))
            predicates.add(criteriaBuilder.equal(root.get(Visit_.VISIT_TYPE_ENUM),
                    searchingParams.getVisitTypeEnum()));

        if (Objects.nonNull(searchingParams.getStartTime()))
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(Visit_.START_TIME), searchingParams.getStartTime()));

        if (Objects.nonNull(searchingParams.getEndTime()))
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(Visit_.END_TIME), searchingParams.getEndTime()));

//        if (StringUtils.isNotEmpty(searchingParams.getUserFirstName())){
//            predicates.add(criteriaBuilder.and(joinUser.get(User_.id).in(searchingParams.getHostId()),
//                    criteriaBuilder.like(criteriaBuilder.upper(joinUser.get(User_.USER_FIRST_NAME)),
//                            "%" + searchingParams.getUserFirstName().toUpperCase() + "%")));
//        }

        predicates.add(criteriaBuilder.notEqual(root.get(Visit_.VISIT_STATUS_ENUM),
                VisitStatusEnum.WAITING));
        predicates.add(criteriaBuilder.notEqual(root.get(Visit_.VISIT_STATUS_ENUM),
                VisitStatusEnum.STARTED));
        predicates.add(joinUser.get(User_.id).in(searchingParams.getUserId()));

        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }

}
