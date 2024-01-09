package org.algotithmcontestdatacollect.displaybackend.repositories.AcRepositories;

import org.algotithmcontestdatacollect.displaybackend.entities.AtcoderEntities.AcSubmitWithUserinfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AcSubmitWithUserinfoRepository extends JpaRepository<AcSubmitWithUserinfo,Long> {
    List<AcSubmitWithUserinfo> getAcSubmitWithUserinfoEntitiesByCid(Long cid);

    @Query(nativeQuery = true,value = "SELECT * FROM ac_submit_with_userinfo WHERE atcoder_id = :atcoder_id and is_after = :isAfter")
    List<AcSubmitWithUserinfo> getAcSubmitWithUserinfoEntitiesByAtcoderIdAndIsAfter(String atcoder_id, Byte isAfter);

    @Query(nativeQuery = true,value = "SELECT * FROM ac_submit_with_userinfo WHERE username = :username and is_after = :isAfter")
    List<AcSubmitWithUserinfo> getAcSubmitWithUserinfoEntitiesByUsernameAndIsAfter(String username, Byte isAfter);

    @Query(nativeQuery = true,value = "SELECT * FROM ac_submit_with_userinfo WHERE acid IN ( SELECT ac_account.id FROM tag_user_map LEFT JOIN ac_account ON ac_account.uid = tag_user_map.uid WHERE tid = :tid)")
    List<AcSubmitWithUserinfo> getAcSubmitWithUserinfoEntitiesByTid(Long tid);
}
