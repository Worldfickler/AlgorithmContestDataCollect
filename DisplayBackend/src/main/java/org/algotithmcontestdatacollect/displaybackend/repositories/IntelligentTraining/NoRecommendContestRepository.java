package org.algotithmcontestdatacollect.displaybackend.repositories.IntelligentTraining;

import org.algotithmcontestdatacollect.displaybackend.entities.IntelligentTraining.NoRecommendContest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NoRecommendContestRepository extends JpaRepository<NoRecommendContest, String>, JpaSpecificationExecutor<NoRecommendContest> {

}