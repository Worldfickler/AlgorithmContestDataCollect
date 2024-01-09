package org.algotithmcontestdatacollect.managebackend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.algotithmcontestdatacollect.managebackend.Entities.School;

public interface SchoolRepository extends JpaRepository<School,Long> {
    School findByName(String name);
}
