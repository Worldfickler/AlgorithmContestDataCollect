package org.algotithmcontestdatacollect.displaybackend.repositories.CfRepository;

import org.algotithmcontestdatacollect.displaybackend.entities.CodeforcesEntities.CfAccountWithUsername;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CodeforcesAccountWitheUsernameRepository extends JpaRepository<CfAccountWithUsername,Long> {
    public List<CfAccountWithUsername> getCfAccountWithUsernameEntitiesByUid(Long uid);
}
