package org.algotithmcontestdatacollect.managebackend.Repositories.AcRepositories;

import org.algotithmcontestdatacollect.managebackend.Entities.AtcoderEntities.AcStucontestWithCinfoUserinfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AcStucontestWithCinfoUserinfoRepository extends JpaRepository<AcStucontestWithCinfoUserinfo,Long> {
    @Query(nativeQuery = true,value = "SELECT DISTINCT(cid) FROM `ac_stucontest_with_cinfo_userinfo`")
    List<Long> getExistCid();

    @Query(nativeQuery = true,value = "SELECT DISTINCT(cid) FROM `ac_stucontest_with_cinfo_userinfo` where cid not in :cids")
    List<Long> getNotInCid(List<Long> cids);

    List<AcStucontestWithCinfoUserinfo> getAcStucontestWithCinfoUserinfoEntitiesByCid(Long cid);
}
