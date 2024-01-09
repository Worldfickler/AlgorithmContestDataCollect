package org.algotithmcontestdatacollect.displaybackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.algotithmcontestdatacollect.displaybackend.entities.School;
public interface SchoolRepository extends JpaRepository<School, Long> {
    Integer countById(Long id);
}
