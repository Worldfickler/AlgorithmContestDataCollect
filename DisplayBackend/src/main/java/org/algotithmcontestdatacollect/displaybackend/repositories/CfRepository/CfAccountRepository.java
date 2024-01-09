package org.algotithmcontestdatacollect.displaybackend.repositories.CfRepository;

import org.algotithmcontestdatacollect.displaybackend.entities.CodeforcesEntities.CfAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CfAccountRepository extends JpaRepository<CfAccount,Long> {

    @Modifying
    @Query(value = "update CfAccount set isMain = 0 where uid = :uid and isMain = 1")
    void resetAllMain(Long uid);

    @Modifying
    @Query(value = "update CfAccount set isMain = 1 where id = :id and uid = :uid")
    void setMainAccount(Long id, Long uid);

    boolean existsByIdAndUid(Long id, Long uid);
}
