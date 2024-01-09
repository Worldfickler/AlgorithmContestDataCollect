package org.algotithmcontestdatacollect.crawlerreceiver.Repositories;

import org.algotithmcontestdatacollect.crawlerreceiver.TableEntity.AcSubmitcode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcSubmitCodeRepository extends JpaRepository<AcSubmitcode, Long> {
    boolean existsAcSubmitCodeEntityBySid(Long sid);
}
