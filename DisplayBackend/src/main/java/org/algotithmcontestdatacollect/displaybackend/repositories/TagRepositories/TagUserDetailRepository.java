package org.algotithmcontestdatacollect.displaybackend.repositories.TagRepositories;

import org.algotithmcontestdatacollect.displaybackend.entities.TagEntities.TagUserDetail;
import org.algotithmcontestdatacollect.displaybackend.entities.TagEntities.TagUserMapPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagUserDetailRepository extends JpaRepository<TagUserDetail, TagUserMapPK> {
    List<TagUserDetail> findAllByTid(Long tid);
}
