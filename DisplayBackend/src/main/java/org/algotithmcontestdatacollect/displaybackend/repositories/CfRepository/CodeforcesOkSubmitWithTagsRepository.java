package org.algotithmcontestdatacollect.displaybackend.repositories.CfRepository;

import org.algotithmcontestdatacollect.displaybackend.entities.CodeforcesEntities.CodeforcesOkSubmitWithTags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CodeforcesOkSubmitWithTagsRepository extends JpaRepository<CodeforcesOkSubmitWithTags,Long> {
    @Query(nativeQuery = true,value = "SELECT * FROM codeforces_ok_submit_with_tags WHERE uid in (SELECT uid FROM tag_user_map WHERE tid = :tid)")
    List<CodeforcesOkSubmitWithTags> getCodeforcesOkSubmitWithTagsEntitiesByTid(Long tid);

    @Query(nativeQuery = true,value = "SELECT * FROM codeforces_ok_submit_with_tags WHERE uid in :uids")
    List<CodeforcesOkSubmitWithTags> getCodeforcesOkSubmitWithTagsEntitiesByUids(List<Long> uids);

    List<CodeforcesOkSubmitWithTags> getCodeforcesOkSubmitWithTagsEntitiesByUid(Long uid);

    @Query(nativeQuery = true,value = "SELECT * FROM codeforces_ok_submit_with_tags WHERE tags LIKE CONCAT('%',:tag,'%') ORDER BY difficulty ASC limit 10")
    List<CodeforcesOkSubmitWithTags> getCodeforcesOkSubmitWithTagsEntitiesByTagsContaining(String tag);
}
