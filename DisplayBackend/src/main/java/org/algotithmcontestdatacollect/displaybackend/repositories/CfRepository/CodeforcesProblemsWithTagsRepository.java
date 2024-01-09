package org.algotithmcontestdatacollect.displaybackend.repositories.CfRepository;

import org.algotithmcontestdatacollect.displaybackend.entities.CodeforcesEntities.CodeforcesProblemsWithTags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CodeforcesProblemsWithTagsRepository
        extends JpaRepository<CodeforcesProblemsWithTags,Integer>,
        JpaSpecificationExecutor<CodeforcesProblemsWithTags> {
    CodeforcesProblemsWithTags getCodeforcesProblemsWithTagsEntityByCidAndQindex(Long cid, String qindex);

    List<CodeforcesProblemsWithTags> getCodeforcesProblemsWithTagsEntitiesByDifficultyBetween(int l, int r);

    @Query(nativeQuery = true,value = "SELECT * FROM codeforces_problems_with_tags WHERE tags LIKE CONCAT('%',:tag,'%') AND difficulty>0 ORDER BY difficulty ASC limit 5")
    List<CodeforcesProblemsWithTags> getCodeforcesProblemsWithTagsEntitiesByTagsContaining(String tag);

    @Query(nativeQuery = true,value = "SELECT * FROM codeforces_problems_with_tags WHERE difficulty > :rating AND tags LIKE CONCAT('%',:tag,'%') ORDER BY difficulty ASC limit 5")
    List<CodeforcesProblemsWithTags> getCodeforcesProblemsWithTagsEntitiesByTagsContainingAndDifficultyGreaterThanEqual(String tag, int rating);

}
