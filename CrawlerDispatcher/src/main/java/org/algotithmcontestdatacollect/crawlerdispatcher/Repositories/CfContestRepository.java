package org.algotithmcontestdatacollect.crawlerdispatcher.Repositories;

import org.algotithmcontestdatacollect.crawlerdispatcher.TableEntity.CfContest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CfContestRepository extends JpaRepository<CfContest,Long> {
    public CfContest getCfContestEntityByCid(Long cid);
}
