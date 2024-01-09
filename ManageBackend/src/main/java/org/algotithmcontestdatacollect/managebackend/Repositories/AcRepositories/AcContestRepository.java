package org.algotithmcontestdatacollect.managebackend.Repositories.AcRepositories;

import org.algotithmcontestdatacollect.managebackend.Entities.AtcoderEntities.AcContest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcContestRepository extends JpaRepository<AcContest,Long> {

}
