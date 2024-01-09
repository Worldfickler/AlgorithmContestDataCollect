package org.algotithmcontestdatacollect.crawlerreceiver.Repositories;

import org.algotithmcontestdatacollect.crawlerreceiver.TableEntity.CfContest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CfContestRepository extends JpaRepository<CfContest,Long> {
     CfContest getCfContestEntityByCid(Long cid);
     Page<CfContest> findByOrderByCidDesc(Pageable pageable);

}
