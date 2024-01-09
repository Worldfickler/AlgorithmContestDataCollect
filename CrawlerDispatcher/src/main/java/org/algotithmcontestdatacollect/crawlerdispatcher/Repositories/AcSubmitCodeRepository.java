package org.algotithmcontestdatacollect.crawlerdispatcher.Repositories;

import org.algotithmcontestdatacollect.crawlerdispatcher.TableEntity.AcSubmitcode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcSubmitCodeRepository extends JpaRepository<AcSubmitcode, Long> {
    boolean existsAcSubmitCodeEntityBySid(Long sid);
}
