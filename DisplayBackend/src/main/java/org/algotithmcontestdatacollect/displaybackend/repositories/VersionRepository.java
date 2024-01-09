package org.algotithmcontestdatacollect.displaybackend.repositories;


import org.algotithmcontestdatacollect.displaybackend.entities.Version;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VersionRepository extends JpaRepository<Version,Integer> {

}
