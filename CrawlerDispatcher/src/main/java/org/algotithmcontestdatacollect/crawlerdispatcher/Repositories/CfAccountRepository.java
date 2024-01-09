package org.algotithmcontestdatacollect.crawlerdispatcher.Repositories;

import org.algotithmcontestdatacollect.crawlerdispatcher.TableEntity.CfAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CfAccountRepository extends JpaRepository<CfAccount,Long> {
    public CfAccount getCfAccountEntityByUid(Long uid);
    public List<CfAccount> getCfAccountEntitiesByUid(Long uid);

    public CfAccount getCfAccountEntityByCodeforcesId(String codeforcesId);
}
