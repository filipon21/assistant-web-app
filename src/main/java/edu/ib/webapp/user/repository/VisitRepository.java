package edu.ib.webapp.user.repository;

import edu.ib.webapp.user.entity.Visit;
import edu.ib.webapp.user.enums.VisitStatusEnum;
import edu.ib.webapp.user.enums.VisitTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface VisitRepository extends JpaRepository<Visit, Long>, JpaSpecificationExecutor<Visit> {

    Visit findFirstByUsers_IdAndVisitStatusEnumOrVisitStatusEnumOrVisitStatusEnumOrderByStartTimeDesc
            (Long id, VisitStatusEnum visitStatusEnum, VisitStatusEnum visitStatusEnum1, VisitStatusEnum visitStatusEnum2);

    Visit findFirstByUsers_IdAndVisitStatusEnumAndVisitTypeEnumIsNotOrVisitTypeEnumIsNotAndVisitStatusEnumOrderByStartTimeDesc(Long id, VisitStatusEnum started, VisitTypeEnum stationary, VisitTypeEnum stationary1, VisitStatusEnum waiting);

    List<Visit> findByStartTimeIsGreaterThanAndStartTimeIsLessThanAndVisitStatusEnum(LocalDateTime of, LocalDateTime of1,
                                                                                     VisitStatusEnum visitStatusEnum);

    Visit findFirstByUsers_IdAndVisitStatusEnum(Long id, VisitStatusEnum started);
}
