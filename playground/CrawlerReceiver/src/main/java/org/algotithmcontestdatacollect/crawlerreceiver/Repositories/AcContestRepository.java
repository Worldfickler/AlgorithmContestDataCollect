package org.algotithmcontestdatacollect.crawlerreceiver.Repositories;

import org.algotithmcontestdatacollect.crawlerreceiver.TableEntity.AcContestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcContestRepository extends JpaRepository<AcContestEntity,Long> {
    public AcContestEntity getAcContestEntityById(Long id);
    public AcContestEntity getAcContestEntityByNickname(String nickname);
    boolean existsAcContestEntityByNickname(String nickname);
    boolean existsAcContestEntityByNicknameAndStartTimeStamp(String nickname,Long startTimeStamp);
}
