package edu.ib.webapp.user.repository.specification;

import edu.ib.webapp.user.entity.Televisit;
import edu.ib.webapp.user.entity.Televisit_;
import edu.ib.webapp.user.entity.User;
import edu.ib.webapp.user.entity.User_;
import edu.ib.webapp.user.enums.TelevisitStatusEnum;
import edu.ib.webapp.user.model.dto.TelevisitSearchingParamsDto;
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
public class TelevisitSpecification implements Specification<Televisit> {

    private TelevisitSearchingParamsDto searchingParams;

    @Override
    public Predicate toPredicate(Root<Televisit> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        Join<Televisit, User> joinUser = root.join(Televisit_.users);
        criteriaQuery.distinct(true);

        if (Objects.nonNull(searchingParams.getTelevisitStatusEnum()))
            predicates.add(criteriaBuilder.equal(root.get(Televisit_.TELEVISIT_STATUS_ENUM),
                    searchingParams.getTelevisitStatusEnum()));

        if (Objects.nonNull(searchingParams.getTelevisitTypeEnum()))
            predicates.add(criteriaBuilder.equal(root.get(Televisit_.TELEVISIT_TYPE_ENUM),
                    searchingParams.getTelevisitTypeEnum()));

        if (Objects.nonNull(searchingParams.getStartTime()))
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(Televisit_.START_TIME), searchingParams.getStartTime()));

        if (Objects.nonNull(searchingParams.getEndTime()))
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(Televisit_.END_TIME), searchingParams.getEndTime()));

//        if (StringUtils.isNotEmpty(searchingParams.getUserFirstName())){
//            predicates.add(criteriaBuilder.and(joinUser.get(User_.id).in(searchingParams.getHostId()),
//                    criteriaBuilder.like(criteriaBuilder.upper(joinUser.get(User_.USER_FIRST_NAME)),
//                            "%" + searchingParams.getUserFirstName().toUpperCase() + "%")));
//        }

        predicates.add(criteriaBuilder.notEqual(root.get(Televisit_.TELEVISIT_STATUS_ENUM),
                TelevisitStatusEnum.WAITING));
        predicates.add(criteriaBuilder.notEqual(root.get(Televisit_.TELEVISIT_STATUS_ENUM),
                TelevisitStatusEnum.STARTED));
        predicates.add(joinUser.get(User_.id).in(searchingParams.getUserId()));

        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }

}
