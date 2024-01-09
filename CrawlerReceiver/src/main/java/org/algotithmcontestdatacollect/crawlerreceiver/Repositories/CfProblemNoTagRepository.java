package org.algotithmcontestdatacollect.crawlerreceiver.Repositories;

import org.algotithmcontestdatacollect.crawlerreceiver.TableEntity.CfProblemNoTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CfProblemNoTagRepository extends JpaRepository<CfProblemNoTag, Void>, JpaSpecificationExecutor<CfProblemNoTag> {
    boolean existsByCidQindex(String cidQindex);
}