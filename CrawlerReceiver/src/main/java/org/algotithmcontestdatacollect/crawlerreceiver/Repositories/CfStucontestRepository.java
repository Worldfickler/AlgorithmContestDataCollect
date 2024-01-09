package org.algotithmcontestdatacollect.crawlerreceiver.Repositories;

import org.algotithmcontestdatacollect.crawlerreceiver.TableEntity.CfStucontest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CfStucontestRepository extends JpaRepository<CfStucontest,Long> {
    public List<CfStucontest> getCfStucontestEntitiesByCfid(Long cfid);
    public boolean existsCfStucontestEntityByCfidAndCid(Long cfid,Long cid);
}
