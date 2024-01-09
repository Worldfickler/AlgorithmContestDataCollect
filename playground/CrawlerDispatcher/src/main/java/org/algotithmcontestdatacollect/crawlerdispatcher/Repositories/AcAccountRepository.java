package org.algotithmcontestdatacollect.crawlerdispatcher.Repositories;

import org.algotithmcontestdatacollect.crawlerdispatcher.TableEntity.AcAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AcAccountRepository extends JpaRepository<AcAccountEntity,Long> {
    public AcAccountEntity getAcAccountEntityByUid(Long uid);
    public List<AcAccountEntity> getAcAccountsEntitiesByUid(Long uid);
    public AcAccountEntity getAcAccountEntityById(Long id);

    public AcAccountEntity getAcAccountEntityByAtcoderId(String atcoderId);
}
