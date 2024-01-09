package org.algotithmcontestdatacollect.managebackend.Repositories.CfRepository;

import org.algotithmcontestdatacollect.managebackend.Entities.CodeforcesEntities.CfAccountWithUsername;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CfAccountWithUsernameRepository extends JpaRepository<CfAccountWithUsername,Long> {
    List<CfAccountWithUsername> findBySchool(Long school);

    Optional<CfAccountWithUsername> findByIdAndSchool(Long id, Long school);
}
