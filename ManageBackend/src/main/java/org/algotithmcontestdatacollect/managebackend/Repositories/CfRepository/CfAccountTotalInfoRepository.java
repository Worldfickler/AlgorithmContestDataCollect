package org.algotithmcontestdatacollect.managebackend.Repositories.CfRepository;

import org.algotithmcontestdatacollect.managebackend.Entities.CodeforcesEntities.CfAccountTotalInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CfAccountTotalInfoRepository extends JpaRepository<CfAccountTotalInfo,Long> {
    String q1 = """
            SELECT
            	*
            FROM
            	cf_account_total_info
            WHERE
            	id IN (
            	SELECT
            		cf_account.id
            	FROM
            		tag_user_map
            		LEFT JOIN cf_account ON cf_account.uid = tag_user_map.uid
            	WHERE
            		tid = :tid
            	);
            """;
    @Query(nativeQuery = true,value = q1)
    List<CfAccountTotalInfo> getCfAccountTotalInfoEntitiesByTid(Long tid);
}
