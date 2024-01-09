package org.algotithmcontestdatacollect.displaybackend.repositories.CfRepository;

import org.algotithmcontestdatacollect.displaybackend.entities.CodeforcesEntities.CfMonthSubmit;
import org.algotithmcontestdatacollect.displaybackend.entities.CodeforcesEntities.CfMonthSubmitPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CfMonthSubmitRepository extends JpaRepository<CfMonthSubmit, CfMonthSubmitPK> {
    String q1 = """
            SELECT
            	*
            FROM
            	cf_month_submit
            WHERE
            	cfid IN (
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
    List<CfMonthSubmit> getCfMonthSubmitEntitiesByTid(Long tid);

}
