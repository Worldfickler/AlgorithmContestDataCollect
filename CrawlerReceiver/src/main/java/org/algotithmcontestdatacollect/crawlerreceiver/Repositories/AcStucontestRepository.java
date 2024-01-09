package org.algotithmcontestdatacollect.crawlerreceiver.Repositories;

import org.algotithmcontestdatacollect.crawlerreceiver.TableEntity.AcStucontest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcStucontestRepository extends JpaRepository<AcStucontest,Long> {

    public boolean existsAcStucontestEntityByAcidAndCid(Long acid,Long cid);
}
