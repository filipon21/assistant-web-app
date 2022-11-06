package edu.ib.webapp.user.repository;

import edu.ib.webapp.user.entity.Refferal;
import edu.ib.webapp.user.enums.DoctorSpecializationEnum;
import edu.ib.webapp.user.enums.RefferalStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefferalRepository extends JpaRepository<Refferal, Long> {
    List<Refferal> findByVisit_Users_IdAndStatusOrderByEndTimeDesc(Long id, RefferalStatusEnum status);

    List<Refferal> findByVisit_Users_IdAndStatusAndDoctorSpecializationEnumOrderByEndTimeDesc(Long id,
                                                                                              RefferalStatusEnum status,
                                                                                              DoctorSpecializationEnum specialization);

    Refferal findByVisit_Users_IdAndDoctorSpecializationEnum(Long id, DoctorSpecializationEnum doctorSpecializationEnum);



}
