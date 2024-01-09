package org.algotithmcontestdatacollect.crawlerreceiver.Repositories;

import org.algotithmcontestdatacollect.crawlerreceiver.TableEntity.CfAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CfAccountRepository extends JpaRepository<CfAccountEntity,Long> {
    public CfAccountEntity getCfAccountEntityByUid(Long uid);
    public List<CfAccountEntity> getCfAccountEntitiesByUid(Long uid);

    public CfAccountEntity getCfAccountEntityByCodeforcesId(String codeforcesId);
}
