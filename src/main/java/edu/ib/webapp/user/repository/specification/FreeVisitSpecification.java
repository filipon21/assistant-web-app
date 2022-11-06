package edu.ib.webapp.user.repository.specification;

import edu.ib.webapp.user.entity.*;
import edu.ib.webapp.user.enums.VisitStatusEnum;
import edu.ib.webapp.user.model.dto.VisitSearchingParamsDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
public class FreeVisitSpecification implements Specification<Visit> {
    private VisitSearchingParamsDto searchingParams;

    @Override
    public Predicate toPredicate(Root<Visit> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        Join<Visit, User> joinDoctor = root.join(Visit_.users);
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

        if (StringUtils.isNotEmpty(searchingParams.getAddress()))
            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get(User_.ADDRESS)),
                    "%" + searchingParams.getAddress().toUpperCase() + "%"));

        if (Objects.nonNull(searchingParams.getDoctorId())){
            predicates.add(joinDoctor.get(User_.DOCTOR).in(searchingParams.getDoctorId()));
        }

        predicates.add((criteriaBuilder.equal(root.get(Visit_.VISIT_STATUS_ENUM), VisitStatusEnum.FREE)));

        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
