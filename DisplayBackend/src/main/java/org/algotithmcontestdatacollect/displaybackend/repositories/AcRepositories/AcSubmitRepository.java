package org.algotithmcontestdatacollect.displaybackend.repositories.AcRepositories;

import org.algotithmcontestdatacollect.displaybackend.entities.AtcoderEntities.AcSubmit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AcSubmitRepository extends JpaRepository<AcSubmit,Long> {
    List<AcSubmit> getAcSubmitEntitiesBySubmitTimeBetween(Long start, Long end);
}
