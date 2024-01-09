package org.algotithmcontestdatacollect.displaybackend.repositories.CfRepository;

import org.algotithmcontestdatacollect.displaybackend.entities.CodeforcesEntities.CfContestWithParticipate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CfContestWithParticipateRepository extends JpaRepository<CfContestWithParticipate,Long> {
    List<CfContestWithParticipate> getCfContestWithParticipateEntitiesByStartTimeStampBetween(Long startTimeStamp, Long endTimeStamp);
}
