package org.algotithmcontestdatacollect.managebackend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.algotithmcontestdatacollect.managebackend.Entities.Application;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application,Long> {
    List<Application> findBySchool(Long school);
}  