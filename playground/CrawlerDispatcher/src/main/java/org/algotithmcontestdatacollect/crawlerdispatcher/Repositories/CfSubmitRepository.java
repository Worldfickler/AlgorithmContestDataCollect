package org.algotithmcontestdatacollect.crawlerdispatcher.Repositories;

import org.algotithmcontestdatacollect.crawlerdispatcher.TableEntity.CfAccountEntity;
import org.algotithmcontestdatacollect.crawlerdispatcher.TableEntity.CfSubmitEntity;
import org.algotithmcontestdatacollect.crawlerdispatcher.TableEntity.CfContestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CfSubmitRepository extends JpaRepository<CfSubmitEntity,Long> {
    public CfSubmitEntity getCfSubmitEntityBySid(Long sid);
    public boolean existsCfSubmitEntityBySid(Long sid);

    @Query(value = "SELECT * FROM cf_submit WHERE sid not in (SELECT sid FROM cf_submitcode) ORDER BY RAND() LIMIT 200",nativeQuery = true)
    List<CfSubmitEntity> get200NoCodeSubmitsRandomly();

}
