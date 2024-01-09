package org.algotithmcontestdatacollect.crawlerreceiver.Repositories;

import org.algotithmcontestdatacollect.crawlerreceiver.TableEntity.AcStucontestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcStucontestRepository extends JpaRepository<AcStucontestEntity,Long> {

    public boolean existsAcStucontestEntityByAcidAndCid(Long acid,Long cid);
}
