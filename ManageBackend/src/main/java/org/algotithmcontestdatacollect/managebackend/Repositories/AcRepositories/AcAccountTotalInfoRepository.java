package org.algotithmcontestdatacollect.managebackend.Repositories.AcRepositories;

import org.algotithmcontestdatacollect.managebackend.Entities.AtcoderEntities.AcAccountTotalInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AcAccountTotalInfoRepository extends JpaRepository<AcAccountTotalInfo,Long> {
    String q1 = """
            SELECT
            	*
            FROM
            	ac_account_total_info
            WHERE
            	id IN (
            	SELECT
            		ac_account.id
            	FROM
            		tag_user_map
            		LEFT JOIN ac_account ON ac_account.uid = tag_user_map.uid
            	WHERE
            		tid = :tid
            	);
            """;
    @Query(nativeQuery = true,value = q1)
    List<AcAccountTotalInfo> getAcAccountTotalInfoEntitiesByTid(Long tid);

}
