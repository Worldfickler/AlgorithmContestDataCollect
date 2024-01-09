package org.algotithmcontestdatacollect.displaybackend.repositories.CfRepository;

import org.algotithmcontestdatacollect.displaybackend.entities.CodeforcesEntities.CfSubmitWithUserinfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CfSubmitWithUserinfoRepository extends JpaRepository<CfSubmitWithUserinfo,Long> {
    List<CfSubmitWithUserinfo> getCfSubmitWithUserinfoEntitiesByCid(Long cid);

    @Query(nativeQuery = true,value = "SELECT * FROM cf_submit_with_userinfo WHERE codeforces_id = :codeforces_id and is_after = :isAfter")
    List<CfSubmitWithUserinfo> getCfSubmitWithUserinfoEntitiesByCodeforcesIdAndIsAfter(String codeforces_id, Byte isAfter);

    @Query(nativeQuery = true,value = "SELECT * FROM cf_submit_with_userinfo WHERE username = :username and is_after = :isAfter")
    List<CfSubmitWithUserinfo> getCfSubmitWithUserinfoEntitiesByUsernameIdAndIsAfter(String username, Byte isAfter);

    @Query(nativeQuery = true,value = "SELECT * FROM cf_submit_with_userinfo WHERE cfid IN ( SELECT cf_account.id FROM tag_user_map LEFT JOIN cf_account ON cf_account.uid = tag_user_map.uid WHERE tid = :tid)")
    List<CfSubmitWithUserinfo> getCfSubmitWithUserinfoEntitiesByTid(Long tid);
}
