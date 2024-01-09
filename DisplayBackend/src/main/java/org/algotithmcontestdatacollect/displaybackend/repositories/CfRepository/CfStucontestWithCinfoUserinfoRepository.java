package org.algotithmcontestdatacollect.displaybackend.repositories.CfRepository;

import org.algotithmcontestdatacollect.displaybackend.entities.CodeforcesEntities.CfStucontestWithCinfoUserinfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CfStucontestWithCinfoUserinfoRepository extends JpaRepository<CfStucontestWithCinfoUserinfo,Long > {
    public List<CfStucontestWithCinfoUserinfo> getCfStucontestWithCinfoUserinfoEntitiesByCodeforcesId(String codeforces_id);
    public List<CfStucontestWithCinfoUserinfo> getCfStucontestWithCinfoUserinfoEntitiesByUsername(String username);

    @Query(nativeQuery = true,value = "SELECT * FROM cf_stucontest_with_cinfo_userinfo WHERE cfid IN ( SELECT cf_account.id FROM tag_user_map LEFT JOIN cf_account ON cf_account.uid = tag_user_map.uid WHERE tid = :tid and is_main = 1 );")
    public List<CfStucontestWithCinfoUserinfo> getCfStucontestWithCinfoUserinfoEntitiesByTid(Long tid);

    List<CfStucontestWithCinfoUserinfo> getCfStucontestWithCinfoUserinfoEntitiesByIsMain(Integer isMain);
    List<CfStucontestWithCinfoUserinfo> getCfStucontestWithCinfoUserinfoEntitiesByIsMainAndSchool(Integer isMain, Long school);
}
