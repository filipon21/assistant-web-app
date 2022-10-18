package edu.ib.webapp.user.repository;

import edu.ib.webapp.user.entity.Assistant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssistantRepository extends JpaRepository<Assistant, Long> {
}
