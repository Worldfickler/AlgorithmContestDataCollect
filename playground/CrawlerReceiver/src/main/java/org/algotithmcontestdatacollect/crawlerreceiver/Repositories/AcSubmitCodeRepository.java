package org.algotithmcontestdatacollect.crawlerreceiver.Repositories;

import org.algotithmcontestdatacollect.crawlerreceiver.TableEntity.AcSubmitcodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcSubmitCodeRepository extends JpaRepository<AcSubmitcodeEntity, Long> {
    boolean existsAcSubmitCodeEntityBySid(Long sid);
}
