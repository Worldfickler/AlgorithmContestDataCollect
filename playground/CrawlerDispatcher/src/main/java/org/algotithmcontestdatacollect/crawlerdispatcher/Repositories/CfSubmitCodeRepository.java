package org.algotithmcontestdatacollect.crawlerdispatcher.Repositories;

import org.algotithmcontestdatacollect.crawlerdispatcher.TableEntity.CfSubmitEntity;
import org.algotithmcontestdatacollect.crawlerdispatcher.TableEntity.CfSubmitcodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CfSubmitCodeRepository extends JpaRepository<CfSubmitcodeEntity,Long> {
    public boolean existsCfSubmitcodeEntityBySid(Long sid);
}
