package io.gig.realestate.domain.role.repository;

import io.gig.realestate.domain.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : JAKE
 * @date : 2023/03/04
 */
@Repository
public interface RoleStoreRepository extends JpaRepository<Role, String> {
}
