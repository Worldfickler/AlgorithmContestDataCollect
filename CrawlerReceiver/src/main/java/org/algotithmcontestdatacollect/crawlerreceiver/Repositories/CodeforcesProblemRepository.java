package org.algotithmcontestdatacollect.crawlerreceiver.Repositories;

import org.algotithmcontestdatacollect.crawlerreceiver.TableEntity.CodeforcesProblems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeforcesProblemRepository extends JpaRepository<CodeforcesProblems,Integer> {
    boolean existsByCidAndQindex(Long cid,String qindex);
    CodeforcesProblems getByCidAndQindex(Long cid, String qindex);
}
