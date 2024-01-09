package org.algotithmcontestdatacollect.crawlerdispatcher.Repositories;

import org.algotithmcontestdatacollect.crawlerdispatcher.TableEntity.AcSubmitcodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcSubmitCodeRepository extends JpaRepository<AcSubmitcodeEntity, Long> {
    boolean existsAcSubmitCodeEntityBySid(Long sid);
}
