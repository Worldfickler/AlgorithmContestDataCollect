package org.algotithmcontestdatacollect.displaybackend.repositories.IntelligentTraining;

import org.algotithmcontestdatacollect.displaybackend.entities.IntelligentTraining.IntelligentTrainingQuestions;
import org.algotithmcontestdatacollect.displaybackend.entities.IntelligentTraining.IntelligentTrainingQuestionsPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IntelligentTrainingQuestionsRepository extends JpaRepository<IntelligentTrainingQuestions, IntelligentTrainingQuestionsPK> {
    List<IntelligentTrainingQuestions> getIntelligentTrainingQuestionsEntitiesByUid(Long uid);
    List<IntelligentTrainingQuestions> getIntelligentTrainingQuestionsEntitiesByTid(Integer tid);

    IntelligentTrainingQuestions getIntelligentTrainingQuestionsEntityByUidAndTid(Long uid, Integer tid);

    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO `graduate`.`intelligent_training_questions`(`uid`, `tid`,`problems`) VALUES (:uid, :tid, :questions) ")
    void insertIntelligentTrainingQuestionsEntityByUidAndTid(Long uid, Integer tid,String questions);


}
