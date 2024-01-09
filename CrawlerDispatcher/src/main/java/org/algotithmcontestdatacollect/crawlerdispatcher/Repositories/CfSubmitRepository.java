package org.algotithmcontestdatacollect.crawlerdispatcher.Repositories;

import org.algotithmcontestdatacollect.crawlerdispatcher.TableEntity.CfSubmit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CfSubmitRepository extends JpaRepository<CfSubmit,Long> {
    public CfSubmit getCfSubmitEntityBySid(Long sid);
    public boolean existsCfSubmitEntityBySid(Long sid);

    @Query(value = "SELECT * FROM cf_submit WHERE sid not in (SELECT sid FROM cf_submitcode) ORDER BY RAND() LIMIT 200",nativeQuery = true)
    List<CfSubmit> get200NoCodeSubmitsRandomly();

}
