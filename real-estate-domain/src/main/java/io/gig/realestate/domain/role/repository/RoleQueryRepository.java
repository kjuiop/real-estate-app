package io.gig.realestate.domain.role.repository;

import io.gig.realestate.domain.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/03/01
 */
@Repository
public interface RoleQueryRepository extends JpaRepository<Role, Long> {

    List<Role> findAllByOrderBySortOrderAsc();

    boolean existsByName(String name);

    Role findByName(String name);
}
