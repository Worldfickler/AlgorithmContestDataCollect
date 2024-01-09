package org.algotithmcontestdatacollect.crawlerreceiver.Repositories;

import org.algotithmcontestdatacollect.crawlerreceiver.TableEntity.SpiderLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpiderLogRepository extends JpaRepository<SpiderLogEntity,Long> {
}
