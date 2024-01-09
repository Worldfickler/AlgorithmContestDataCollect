package org.algotithmcontestdatacollect.displaybackend.repositories.AcRepositories;

import org.algotithmcontestdatacollect.displaybackend.entities.AtcoderEntities.AcMonthSubmit;
import org.algotithmcontestdatacollect.displaybackend.entities.AtcoderEntities.AcMonthSubmitPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AcMonthSubmitRepository extends JpaRepository<AcMonthSubmit, AcMonthSubmitPK> {
    String q1 = """
            SELECT
            	*
            FROM
            	ac_month_submit
            WHERE
            	acid IN (
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
    List<AcMonthSubmit> getAcMonthSubmitEntitiesByTid(Long tid);
}
