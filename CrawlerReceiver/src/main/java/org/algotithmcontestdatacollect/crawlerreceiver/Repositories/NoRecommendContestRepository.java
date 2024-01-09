package org.algotithmcontestdatacollect.crawlerreceiver.Repositories;

import org.algotithmcontestdatacollect.crawlerreceiver.TableEntity.NoRecommendContest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NoRecommendContestRepository extends JpaRepository<NoRecommendContest, String>, JpaSpecificationExecutor<NoRecommendContest> {
    @Transactional
    void deleteByReason(Integer reason);
    List<NoRecommendContest> findByReason(Integer reason);
    @Transactional
    void deleteByCid(Long cid);
}