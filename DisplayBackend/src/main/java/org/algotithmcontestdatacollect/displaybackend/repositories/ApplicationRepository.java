package org.algotithmcontestdatacollect.displaybackend.repositories;

import org.algotithmcontestdatacollect.displaybackend.entities.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application,Long> {
    List<Application> findByOpertationAndStatus(String operation,byte status);
}
