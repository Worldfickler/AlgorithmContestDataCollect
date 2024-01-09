package org.algotithmcontestdatacollect.managebackend.Repositories;

import org.algotithmcontestdatacollect.managebackend.Entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag,Long> {
}
