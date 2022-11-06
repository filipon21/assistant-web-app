package edu.ib.webapp.user.repository;

import edu.ib.webapp.user.entity.Exemption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExemptionRepository extends JpaRepository<Exemption, Long> {
}
