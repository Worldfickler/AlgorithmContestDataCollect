package org.algotithmcontestdatacollect.displaybackend.repositories;

import org.algotithmcontestdatacollect.displaybackend.entities.ProblemTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemTagRepository extends JpaRepository<ProblemTag,Integer> {
}
