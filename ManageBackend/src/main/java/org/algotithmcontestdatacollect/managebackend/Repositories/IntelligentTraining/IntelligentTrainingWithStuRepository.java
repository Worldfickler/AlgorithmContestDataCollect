package org.algotithmcontestdatacollect.managebackend.Repositories.IntelligentTraining;

import org.algotithmcontestdatacollect.managebackend.Entities.IntelligentTraining.IntelligentTrainingWithStu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface IntelligentTrainingWithStuRepository extends JpaRepository<IntelligentTrainingWithStu, Void>, JpaSpecificationExecutor<IntelligentTrainingWithStu> {
    List<IntelligentTrainingWithStu> getIntelligentTrainingWithStuEntitiesByTid(Integer tid);
}