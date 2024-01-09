package org.algotithmcontestdatacollect.displaybackend.repositories.AcRepositories;

import org.algotithmcontestdatacollect.displaybackend.entities.AtcoderEntities.AcStucontestWithUserinfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AcStucontestWithUserinfoRepository extends JpaRepository<AcStucontestWithUserinfo,Long> {
    List<AcStucontestWithUserinfo> getAcStucontestWithUserinfoEntitiesByCid(Long cid);

    List<AcStucontestWithUserinfo> getAcStucontestWithUserinfoEntitiesByAtcoderId(String atcoder_id);
}
