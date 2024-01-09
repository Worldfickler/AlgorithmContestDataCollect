package org.algotithmcontestdatacollect.managebackend.Repositories.CfRepository;

import org.algotithmcontestdatacollect.managebackend.Entities.CodeforcesEntities.CfStucontestWithCinfoUserinfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CfStucontestWithCinfoUserinfoRepository extends JpaRepository<CfStucontestWithCinfoUserinfo,Long> {
    @Query(nativeQuery = true,value = "SELECT DISTINCT(cid) FROM `cf_stucontest_with_cinfo_userinfo`")
    List<Long> getExistCid();

    @Query(nativeQuery = true,value = "SELECT DISTINCT(cid) FROM `cf_stucontest_with_cinfo_userinfo` where cid not in :cids")
    List<Long> getNotInCid(List<Long> cids);

    List<CfStucontestWithCinfoUserinfo> getCfStucontestWithCinfoUserinfoEntitiesByCid(Long cid);
}
