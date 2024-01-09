package org.algotithmcontestdatacollect.crawlerdispatcher.Repositories;

import org.algotithmcontestdatacollect.crawlerdispatcher.TableEntity.AcAccountWithUsernameEntity;
import org.algotithmcontestdatacollect.crawlerdispatcher.TableEntity.CfAccountWithUsernameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CfAccountWithUsernameRepository extends JpaRepository<CfAccountWithUsernameEntity,Long> {
    List<CfAccountWithUsernameEntity> getCfAccountWithUsernameEntitiesByYearAfter(Integer year);
}
