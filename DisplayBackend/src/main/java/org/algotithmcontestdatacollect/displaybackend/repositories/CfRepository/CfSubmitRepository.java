package org.algotithmcontestdatacollect.displaybackend.repositories.CfRepository;

import org.algotithmcontestdatacollect.displaybackend.entities.CodeforcesEntities.CfSubmit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CfSubmitRepository extends JpaRepository<CfSubmit,Long> {
    List<CfSubmit> getCfSubmitEntitiesByCid(Long cid);
    List<CfSubmit> getCfSubmitEntitiesBySubmitTimeBetween(Long start, Long end);
    boolean existsBySid(Long sid);
    List<CfSubmit>findByStatus(String status);
}
