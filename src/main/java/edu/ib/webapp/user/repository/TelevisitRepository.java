package edu.ib.webapp.user.repository;

import edu.ib.webapp.user.entity.Televisit;
import edu.ib.webapp.user.enums.TelevisitStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TelevisitRepository extends JpaRepository<Televisit, Long> {

    List<Televisit> findByUsers_idAndTelevisitStatusEnum(Long id, TelevisitStatusEnum status);


}
