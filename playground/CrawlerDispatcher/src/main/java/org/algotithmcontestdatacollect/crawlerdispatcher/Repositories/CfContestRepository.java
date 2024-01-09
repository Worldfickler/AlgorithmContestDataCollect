package org.algotithmcontestdatacollect.crawlerdispatcher.Repositories;

import org.algotithmcontestdatacollect.crawlerdispatcher.TableEntity.CfContestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CfContestRepository extends JpaRepository<CfContestEntity,Long> {
    public CfContestEntity getCfContestEntityByCid(Long cid);
}
