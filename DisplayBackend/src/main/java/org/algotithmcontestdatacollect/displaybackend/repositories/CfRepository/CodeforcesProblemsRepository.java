package org.algotithmcontestdatacollect.displaybackend.repositories.CfRepository;

import org.algotithmcontestdatacollect.displaybackend.entities.CodeforcesEntities.CodeforcesProblems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CodeforcesProblemsRepository extends JpaRepository<CodeforcesProblems, String>, JpaSpecificationExecutor<CodeforcesProblems> {

}