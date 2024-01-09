package org.algotithmcontestdatacollect.displaybackend.repositories;

import org.algotithmcontestdatacollect.displaybackend.entities.ApplicationWithUserinfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationWithUserinfoRepository extends JpaRepository<ApplicationWithUserinfo,Long> {
    List<ApplicationWithUserinfo> getApplicationWithUserinfoEntitiesByUid(Long uid);
}
