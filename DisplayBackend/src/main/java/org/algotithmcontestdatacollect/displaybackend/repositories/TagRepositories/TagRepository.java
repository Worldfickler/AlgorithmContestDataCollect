package org.algotithmcontestdatacollect.displaybackend.repositories.TagRepositories;

import org.algotithmcontestdatacollect.displaybackend.entities.TagEntities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag,Long> {
}
