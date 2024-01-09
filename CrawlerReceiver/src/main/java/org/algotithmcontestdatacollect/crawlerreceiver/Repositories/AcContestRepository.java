package org.algotithmcontestdatacollect.crawlerreceiver.Repositories;

import org.algotithmcontestdatacollect.crawlerreceiver.TableEntity.AcContest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcContestRepository extends JpaRepository<AcContest,Long> {
    public AcContest getAcContestEntityById(Long id);
    public AcContest getAcContestEntityByNickname(String nickname);
    boolean existsAcContestEntityByNickname(String nickname);
    boolean existsAcContestEntityByNicknameAndStartTimeStamp(String nickname,Long startTimeStamp);
}
