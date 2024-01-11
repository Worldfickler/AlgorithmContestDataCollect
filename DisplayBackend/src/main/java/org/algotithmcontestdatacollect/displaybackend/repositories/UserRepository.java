package org.algotithmcontestdatacollect.displaybackend.repositories;

import org.algotithmcontestdatacollect.displaybackend.entities.NormalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<NormalUser, Long> {
    public NormalUser findByUsername(String username);

    @Query(nativeQuery = true, value = "SELECT id FROM normal_user")
    List<Long> getAllUserId();

    boolean existsBySchoolAndStuNo(Long school, String stuNo);

    @Query(nativeQuery = true, value = "SELECT * FROM normal_user where id in :ids")
    List<NormalUser> findByIds(List<Long> ids);
}
