package org.algotithmcontestdatacollect.displaybackend.repositories.IntelligentTraining;


import org.algotithmcontestdatacollect.displaybackend.entities.IntelligentTraining.CfProblemNoTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CfProblemNoTagRepository extends JpaRepository<CfProblemNoTag, Void>, JpaSpecificationExecutor<CfProblemNoTag> {
    boolean existsByCidQindex(String cidQindex);
}