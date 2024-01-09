package org.algotithmcontestdatacollect.crawlerreceiver.Repositories;

import org.algotithmcontestdatacollect.crawlerreceiver.TableEntity.CfSubmitcode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CfSubmitCodeRepository extends JpaRepository<CfSubmitcode,Long> {
    public boolean existsCfSubmitcodeEntityBySid(Long sid);
}
