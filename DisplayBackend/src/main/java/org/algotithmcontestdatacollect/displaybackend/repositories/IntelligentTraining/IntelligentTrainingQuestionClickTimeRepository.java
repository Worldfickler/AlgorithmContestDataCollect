package org.algotithmcontestdatacollect.displaybackend.repositories.IntelligentTraining;

import org.algotithmcontestdatacollect.displaybackend.entities.IntelligentTraining.IntelligentTrainingQuestionClickTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IntelligentTrainingQuestionClickTimeRepository extends JpaRepository<IntelligentTrainingQuestionClickTime, String>, JpaSpecificationExecutor<IntelligentTrainingQuestionClickTime> {
    IntelligentTrainingQuestionClickTime findByUidAndCidAndQindex(Long uid,Long cid,String qIndex);


}