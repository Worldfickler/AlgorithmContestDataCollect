package org.algotithmcontestdatacollect.crawlerreceiver.Repositories;

import org.algotithmcontestdatacollect.crawlerreceiver.TableEntity.AcSubmitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcSubmitRepository extends JpaRepository<AcSubmitEntity, Long> {
    public AcSubmitEntity getAcSubmitEntityBySid(Long sid);
    boolean existsAcSubmitEntityByAcidAndCid(Long acid, Long cid);
    boolean existsAcSubmitEntityBySid(Long sid);
}
