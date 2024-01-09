package org.algotithmcontestdatacollect.displaybackend.repositories.TagRepositories;

import org.algotithmcontestdatacollect.displaybackend.entities.TagEntities.TagUserMap;
import org.algotithmcontestdatacollect.displaybackend.entities.TagEntities.TagUserMapPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagUserMapRepository extends JpaRepository<TagUserMap, TagUserMapPK> {
    void deleteAllByTid(Long tid);

    @Modifying
    @Query(nativeQuery = true,value = "INSERT INTO tag_user_map(uid,tid) VALUES (:uid,:tid)")
    void insertByUidAndTid(Long uid,Long tid);



    @Query(nativeQuery = true,value = "SELECT uid from tag_user_map where tid = :tid")
    List<Long> getUidsByTid(Long tid);
}
