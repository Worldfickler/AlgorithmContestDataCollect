package org.algotithmcontestdatacollect.managebackend.Repositories;

import org.algotithmcontestdatacollect.managebackend.Entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Long> {
    public Admin findByUsername(String username);
}
