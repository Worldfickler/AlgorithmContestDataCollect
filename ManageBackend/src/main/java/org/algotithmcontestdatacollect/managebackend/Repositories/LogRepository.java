package org.algotithmcontestdatacollect.managebackend.Repositories;


import org.algotithmcontestdatacollect.managebackend.Entities.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepository extends JpaRepository<Log,Long> {
    List<Log> getLogEntitiesByUid(Long uid);
}
