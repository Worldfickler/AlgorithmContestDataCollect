package org.algotithmcontestdatacollect.crawlerreceiver.Repositories;

import org.algotithmcontestdatacollect.crawlerreceiver.TableEntity.SpiderLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpiderLogRepository extends JpaRepository<SpiderLog,Long> {
}
