package org.algotithmcontestdatacollect.crawlerdispatcher.Repositories;

import org.algotithmcontestdatacollect.crawlerdispatcher.TableEntity.CfSubmitcode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CfSubmitCodeRepository extends JpaRepository<CfSubmitcode,Long> {
    public boolean existsCfSubmitcodeEntityBySid(Long sid);
}
