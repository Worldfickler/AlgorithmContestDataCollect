package org.algotithmcontestdatacollect.crawlerdispatcher.Repositories;

import org.algotithmcontestdatacollect.crawlerdispatcher.TableEntity.AcSubmitEntity;
import org.algotithmcontestdatacollect.crawlerdispatcher.TableEntity.CfSubmitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AcSubmitRepository extends JpaRepository<AcSubmitEntity, Long> {
    public AcSubmitEntity getAcSubmitEntityBySid(Long sid);
    boolean existsAcSubmitEntityByAcidAndCid(Long acid, Long cid);
    boolean existsAcSubmitEntityBySid(Long sid);

    @Query(value = "SELECT * FROM ac_submit WHERE sid not in (SELECT sid FROM ac_submitcode) ORDER BY RAND() LIMIT 200",nativeQuery = true)
    List<AcSubmitEntity> get500NoCodeSubmitsRandomly();
}
