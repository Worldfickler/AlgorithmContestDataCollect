package org.algotithmcontestdatacollect.managebackend.Repositories;

import org.algotithmcontestdatacollect.managebackend.Entities.TagUserMap;
import org.algotithmcontestdatacollect.managebackend.Entities.TagUserMapPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TagUserMapRepository extends JpaRepository<TagUserMap, TagUserMapPK> {
    void deleteAllByTid(Long tid);

    @Modifying
    @Query(nativeQuery = true,value = "INSERT INTO tag_user_map(uid,tid) VALUES (:uid,:tid)")
    void insertByUidAndTid(Long uid,Long tid);

    void  deleteByTidAndUid(Long tid,Long uid);
}
