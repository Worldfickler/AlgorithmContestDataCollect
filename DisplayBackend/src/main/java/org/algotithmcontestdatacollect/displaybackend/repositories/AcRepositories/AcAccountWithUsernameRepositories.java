package org.algotithmcontestdatacollect.displaybackend.repositories.AcRepositories;

import org.algotithmcontestdatacollect.displaybackend.entities.AtcoderEntities.AcAccountWithUsername;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AcAccountWithUsernameRepositories extends JpaRepository<AcAccountWithUsername,Long> {
    List<AcAccountWithUsername> getAcAccountWithUsernameEntitiesByUid(Long uid);
}
