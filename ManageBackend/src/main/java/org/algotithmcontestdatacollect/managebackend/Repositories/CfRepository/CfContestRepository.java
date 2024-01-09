package org.algotithmcontestdatacollect.managebackend.Repositories.CfRepository;

import org.algotithmcontestdatacollect.managebackend.Entities.CodeforcesEntities.CfContest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CfContestRepository extends JpaRepository<CfContest,Long> {

}
