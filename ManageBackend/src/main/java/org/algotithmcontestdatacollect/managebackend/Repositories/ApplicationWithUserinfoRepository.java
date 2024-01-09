package org.algotithmcontestdatacollect.managebackend.Repositories;

import org.algotithmcontestdatacollect.managebackend.Entities.ApplicationWithUserinfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationWithUserinfoRepository extends JpaRepository<ApplicationWithUserinfo,Long> {
    List<ApplicationWithUserinfo> findBySchool(Long school);
}
