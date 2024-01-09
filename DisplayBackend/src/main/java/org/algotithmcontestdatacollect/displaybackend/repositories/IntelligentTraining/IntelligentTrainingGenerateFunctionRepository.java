package org.algotithmcontestdatacollect.displaybackend.repositories.IntelligentTraining;

import org.algotithmcontestdatacollect.displaybackend.entities.IntelligentTraining.IntelligentTrainingGenerateFunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IntelligentTrainingGenerateFunctionRepository extends JpaRepository<IntelligentTrainingGenerateFunction, Integer> {
    @Query(value = "SELECT * FROM `intelligent_training_generate_function` WHERE `name` in :nameList",nativeQuery = true)
    List<IntelligentTrainingGenerateFunction> getFuncNameByNameList(List<String> nameList);
}