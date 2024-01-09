package org.algotithmcontestdatacollect.displaybackend.repositories;

import org.algotithmcontestdatacollect.displaybackend.entities.Prize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface PrizeRepository extends JpaRepository<Prize, String>, JpaSpecificationExecutor<Prize> {
    List<Prize>findByStatusAndUidIn(String status,List<Long>uids);
    List<Prize>findByUidIn(List<Long>uids);
}