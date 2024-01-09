package org.algotithmcontestdatacollect.displaybackend.repositories.AcRepositories;

import org.algotithmcontestdatacollect.displaybackend.entities.AtcoderEntities.AcAccountTotalInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AcAccountTotalInfoRepository extends JpaRepository<AcAccountTotalInfo,Long> {
    @Query(nativeQuery = true,value = "SELECT * FROM `ac_account_total_info` ORDER BY rating DESC LIMIT :limit")
    List<AcAccountTotalInfo> getTopByRatingOrderByRatingDesc(int limit);
}
