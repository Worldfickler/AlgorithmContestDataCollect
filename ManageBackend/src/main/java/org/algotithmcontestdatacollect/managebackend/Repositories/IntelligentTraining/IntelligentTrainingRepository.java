package org.algotithmcontestdatacollect.managebackend.Repositories.IntelligentTraining;

import org.algotithmcontestdatacollect.managebackend.Entities.IntelligentTraining.IntelligentTraining;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IntelligentTrainingRepository extends JpaRepository<IntelligentTraining,Integer> {
    List<IntelligentTraining> findByStartTimestampLessThanEqualAndEndTimestampGreaterThanEqual(java.util.Date currentDate1, java.util.Date currentDate2);
    List<IntelligentTraining> findBysRatingIsNullAndEndTimestampLessThanEqual(java.util.Date currentDate1);
}

