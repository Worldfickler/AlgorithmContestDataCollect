package org.algotithmcontestdatacollect.crawlerdispatcher.Repositories;

import org.algotithmcontestdatacollect.crawlerdispatcher.TableEntity.CfAccountWithUsername;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CfAccountWithUsernameRepository extends JpaRepository<CfAccountWithUsername,Long> {
    List<CfAccountWithUsername> getCfAccountWithUsernameEntitiesByYearAfter(Integer year);
}
