package org.algotithmcontestdatacollect.managebackend.Repositories.AcRepositories;

import org.algotithmcontestdatacollect.managebackend.Entities.AtcoderEntities.AcAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AcAccountRepository extends JpaRepository<AcAccount,Long> {

    int countByUid(Long uid);

    @Modifying
    @Query(value = "update AcAccount set isMain = 0 where uid = :uid and isMain = 1")
    void resetAllMain(Long uid);

    boolean existsByAtcoderId(String atcoderId);
}
