package org.algotithmcontestdatacollect.displaybackend.repositories.CfRepository;

import org.algotithmcontestdatacollect.displaybackend.entities.CodeforcesEntities.CfStucontestWithUserinfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CfStucontestWithUserinfoRepository extends JpaRepository<CfStucontestWithUserinfo,Long> {
    List<CfStucontestWithUserinfo> getCfStucontestWithUserinfoEntitiesByCid(Long cid);

    List<CfStucontestWithUserinfo> getCfStucontestWithUserinfoEntitiesByCodeforcesId(String codeforces_id);
}
