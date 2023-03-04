package io.gig.realestate.domain.admin.repository;

import io.gig.realestate.domain.admin.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : JAKE
 * @date : 2023/03/01
 */
@Repository
public interface AdministratorQueryRepository extends JpaRepository<Administrator, Long> {
}
