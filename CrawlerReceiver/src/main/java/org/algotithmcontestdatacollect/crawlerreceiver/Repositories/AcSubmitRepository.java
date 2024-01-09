package org.algotithmcontestdatacollect.crawlerreceiver.Repositories;

import org.algotithmcontestdatacollect.crawlerreceiver.TableEntity.AcSubmit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcSubmitRepository extends JpaRepository<AcSubmit, Long> {
    public AcSubmit getAcSubmitEntityBySid(Long sid);
    boolean existsAcSubmitEntityByAcidAndCid(Long acid, Long cid);
    boolean existsAcSubmitEntityBySid(Long sid);
}
