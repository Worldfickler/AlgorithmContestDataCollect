package org.algotithmcontestdatacollect.crawlerreceiver.Repositories;

import org.algotithmcontestdatacollect.crawlerreceiver.TableEntity.CfSubmit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CfSubmitRepository extends JpaRepository<CfSubmit, Long> {
    CfSubmit getCfSubmitEntityBySid(Long sid);

    boolean existsCfSubmitEntityBySid(Long sid);

}
