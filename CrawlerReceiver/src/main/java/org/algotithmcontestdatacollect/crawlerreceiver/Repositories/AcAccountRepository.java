package org.algotithmcontestdatacollect.crawlerreceiver.Repositories;

import org.algotithmcontestdatacollect.crawlerreceiver.TableEntity.AcAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AcAccountRepository extends JpaRepository<AcAccount, Long> {
    AcAccount getAcAccountEntityByUid(Long uid);

    List<AcAccount> getAcAccountsEntitiesByUid(Long uid);

    AcAccount getAcAccountEntityById(Long id);

    AcAccount getAcAccountEntityByAtcoderId(String atcoderId);
}
