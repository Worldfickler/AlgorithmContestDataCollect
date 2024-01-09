package org.algotithmcontestdatacollect.displaybackend.repositories.CfRepository;

import org.algotithmcontestdatacollect.displaybackend.entities.CodeforcesEntities.CfAccountTotalInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CfAccountTotalInfoRepository extends JpaRepository<CfAccountTotalInfo,Long> {
    @Query(nativeQuery = true,value = "SELECT * FROM `cf_account_total_info` ORDER BY rating DESC LIMIT :limit")
    List<CfAccountTotalInfo> getTopByRatingOrderByRatingDesc(int limit);
}
