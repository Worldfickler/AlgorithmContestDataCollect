package org.algotithmcontestdatacollect.crawlerreceiver.Repositories;

import org.algotithmcontestdatacollect.crawlerreceiver.TableEntity.CfSubmitcodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CfSubmitCodeRepository extends JpaRepository<CfSubmitcodeEntity,Long> {
    public boolean existsCfSubmitcodeEntityBySid(Long sid);
}
