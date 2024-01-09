package org.algotithmcontestdatacollect.crawlerreceiver.Repositories;

import org.algotithmcontestdatacollect.crawlerreceiver.TableEntity.ProblemTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemTagRepository extends JpaRepository<ProblemTagEntity,Integer> {
}
