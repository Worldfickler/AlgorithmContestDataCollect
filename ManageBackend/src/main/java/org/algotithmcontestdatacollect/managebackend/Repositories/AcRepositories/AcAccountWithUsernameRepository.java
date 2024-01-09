package org.algotithmcontestdatacollect.managebackend.Repositories.AcRepositories;

import org.algotithmcontestdatacollect.managebackend.Entities.AtcoderEntities.AcAccountWithUsername;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AcAccountWithUsernameRepository extends JpaRepository<AcAccountWithUsername,Long> {
    List<AcAccountWithUsername> findBySchool(Long school);

    Optional<AcAccountWithUsername> findByIdAndSchool(Long id, Long school);
}
