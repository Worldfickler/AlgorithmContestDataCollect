package org.algotithmcontestdatacollect.displaybackend.repositories.CfRepository;

import org.algotithmcontestdatacollect.displaybackend.entities.CodeforcesEntities.CfProblemsWithContestInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CfProblemsWithContestInfoRepository extends JpaRepository<CfProblemsWithContestInfo,Long> {
    List<CfProblemsWithContestInfo> getCfProblemsWithContestInfoEntitiesByLevelAndQindex(Integer level, String qindex);
}
