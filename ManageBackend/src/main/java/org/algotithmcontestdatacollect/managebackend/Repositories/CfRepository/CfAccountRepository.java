package org.algotithmcontestdatacollect.managebackend.Repositories.CfRepository;

import org.algotithmcontestdatacollect.managebackend.Entities.CodeforcesEntities.CfAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CfAccountRepository extends JpaRepository<CfAccount,Long> {
    public int countByUid(Long uid);
    public CfAccount findByUidAndIsMain(Long uid, byte isMain);
    @Modifying
    @Query(value = "update CfAccount set isMain = 0 where uid = :uid and isMain = 1")
    public void resetAllMain(Long uid);

    boolean existsByCodeforcesId(String codeforcesId);
}
