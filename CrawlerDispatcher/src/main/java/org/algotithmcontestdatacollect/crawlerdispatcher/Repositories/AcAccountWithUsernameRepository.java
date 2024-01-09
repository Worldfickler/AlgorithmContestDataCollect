package org.algotithmcontestdatacollect.crawlerdispatcher.Repositories;

import org.algotithmcontestdatacollect.crawlerdispatcher.TableEntity.AcAccountWithUsername;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AcAccountWithUsernameRepository extends JpaRepository<AcAccountWithUsername,Long> {
    List<AcAccountWithUsername> getAcAccountWithUsernameEntitiesByYearAfter(Integer year);

}
