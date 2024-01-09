package org.algotithmcontestdatacollect.crawlerreceiver.Repositories;

import org.algotithmcontestdatacollect.crawlerreceiver.TableEntity.CfContestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CfContestRepository extends JpaRepository<CfContestEntity,Long> {
    public CfContestEntity getCfContestEntityByCid(Long cid);

}
