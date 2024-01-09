package org.algotithmcontestdatacollect.crawlerdispatcher.Repositories;

import org.algotithmcontestdatacollect.crawlerdispatcher.TableEntity.AcAccountWithUsernameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AcAccountWithUsernameRepository extends JpaRepository<AcAccountWithUsernameEntity,Long> {
    List<AcAccountWithUsernameEntity> getAcAccountWithUsernameEntitiesByYearAfter(Integer year);

}
