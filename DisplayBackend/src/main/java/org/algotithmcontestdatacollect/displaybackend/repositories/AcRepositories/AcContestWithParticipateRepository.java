package org.algotithmcontestdatacollect.displaybackend.repositories.AcRepositories;

import org.algotithmcontestdatacollect.displaybackend.entities.AtcoderEntities.AcContestWithParticipate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AcContestWithParticipateRepository extends JpaRepository<AcContestWithParticipate,Long> {
    List<AcContestWithParticipate> getAcContestWithParticipateEntitiesByStartTimeStampBetween(Long startTimeStamp, Long endTimeStamp);
}
