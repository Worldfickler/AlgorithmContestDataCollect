package org.algotithmcontestdatacollect.managebackend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.algotithmcontestdatacollect.managebackend.Entities.NormalUser;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NormalUserRepository extends JpaRepository<NormalUser, Long> {
    List<NormalUser> getNormalUserEntitiesBySchool(Long school);

    boolean existsByUsername(String username);
    @Query(nativeQuery = true,value = "SELECT * FROM normal_user where id in :ids")
    List<NormalUser>findByIds(List<Long>ids);

}
