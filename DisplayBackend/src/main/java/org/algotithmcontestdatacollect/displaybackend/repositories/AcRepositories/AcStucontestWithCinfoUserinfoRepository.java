package org.algotithmcontestdatacollect.displaybackend.repositories.AcRepositories;

import org.algotithmcontestdatacollect.displaybackend.entities.AtcoderEntities.AcStucontestWithCinfoUserinfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AcStucontestWithCinfoUserinfoRepository extends JpaRepository<AcStucontestWithCinfoUserinfo,Long> {
    List<AcStucontestWithCinfoUserinfo> getAcStucontestWithCinfoUserinfoEntitiesByAtcoderId(String atcoder_id);
    List<AcStucontestWithCinfoUserinfo> getAcStucontestWithCinfoUserinfoEntitiesByUsername(String username);

    @Query(nativeQuery = true,value = "SELECT * FROM ac_stucontest_with_cinfo_userinfo WHERE acid IN ( SELECT ac_account.id FROM tag_user_map LEFT JOIN ac_account ON ac_account.uid = tag_user_map.uid WHERE tid = :tid and is_main = 1 );")
    List<AcStucontestWithCinfoUserinfo> getAcStucontestWithCinfoUserinfoEntitiesByTid(Long tid);
}
