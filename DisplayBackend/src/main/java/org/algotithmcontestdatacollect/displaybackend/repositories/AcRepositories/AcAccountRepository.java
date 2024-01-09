package org.algotithmcontestdatacollect.displaybackend.repositories.AcRepositories;

import org.algotithmcontestdatacollect.displaybackend.entities.AtcoderEntities.AcAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AcAccountRepository extends JpaRepository<AcAccount,Long> {
    @Modifying
    @Query(value = "update AcAccount set isMain = 0 where uid = :uid and isMain = 1")
    void resetAllMain(Long uid);

    @Modifying
    @Query(value = "update AcAccount set isMain = 1 where id = :id and uid = :uid")
    void setMainAccount(Long id, Long uid);

    boolean existsByIdAndUid(Long id, Long uid);
}
