package org.algotithmcontestdatacollect.crawlerdispatcher.Repositories;

import org.algotithmcontestdatacollect.crawlerdispatcher.TableEntity.AcAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AcAccountRepository extends JpaRepository<AcAccount,Long> {
    public AcAccount getAcAccountEntityByUid(Long uid);
    public List<AcAccount> getAcAccountsEntitiesByUid(Long uid);
    public AcAccount getAcAccountEntityById(Long id);

    public AcAccount getAcAccountEntityByAtcoderId(String atcoderId);
}
