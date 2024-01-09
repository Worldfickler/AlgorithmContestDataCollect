package org.algotithmcontestdatacollect.crawlerreceiver.Repositories;

import org.algotithmcontestdatacollect.crawlerreceiver.TableEntity.CfSubmitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CfSubmitRepository extends JpaRepository<CfSubmitEntity,Long> {
    public CfSubmitEntity getCfSubmitEntityBySid(Long sid);
    public boolean existsCfSubmitEntityBySid(Long sid);

}
