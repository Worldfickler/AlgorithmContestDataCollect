package org.algotithmcontestdatacollect.crawlerdispatcher.Repositories;

import org.algotithmcontestdatacollect.crawlerdispatcher.TableEntity.AcStucontestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AcStucontestRepository extends JpaRepository<AcStucontestEntity,Long> {

    public boolean existsAcStucontestEntityByAcidAndCid(Long acid,Long cid);
    @Query(value = "SELECT ac_stucontest.* FROM ac_stucontest LEFT JOIN ac_contest on ac_stucontest.cid = ac_contest.id WHERE ac_stucontest.acid = :acid ORDER BY ac_contest.end_time_stamp DESC LIMIT :limit",nativeQuery = true)
    public List<AcStucontestEntity> getRecentContestsByAcid(@Param("acid") Long acid,@Param("limit") Integer limit);

    @Query(value = "SELECT ac_stucontest.* FROM ac_stucontest LEFT JOIN ac_contest on ac_stucontest.cid = ac_contest.id WHERE ac_stucontest.acid = :acid and ac_stucontest.cid in :cids",nativeQuery = true)
    public List<AcStucontestEntity> getRecentContestsByAcidAndCid(@Param("acid") Long acid,@Param("cids") List<Long> cids);

    @Query(value = "SELECT * FROM `ac_stucontest` acstuc WHERE not EXISTS (SELECT * FROM `ac_submit` acsub WHERE acstuc.acid = acsub.acid and acstuc.cid = acsub.cid ) and diff <> 0",nativeQuery = true)
    List<AcStucontestEntity> getNoSubmitStuContest();
}
