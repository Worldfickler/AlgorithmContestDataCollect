package org.algotithmcontestdatacollect.displaybackend.repositories.CfRepository;

import org.algotithmcontestdatacollect.displaybackend.entities.CodeforcesEntities.CfContestMaxsolve;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CfContestMaxsolveRepository extends JpaRepository<CfContestMaxsolve,Long> {
    List<CfContestMaxsolve> getCfContestMaxsolveEntitiesByUsernameOrderByStartTimeStampDesc(String username);
}
