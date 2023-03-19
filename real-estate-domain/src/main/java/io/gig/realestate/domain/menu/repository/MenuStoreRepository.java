package io.gig.realestate.domain.menu.repository;

import io.gig.realestate.domain.menu.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : JAKE
 * @date : 2023/03/19
 */
@Repository
public interface MenuStoreRepository extends JpaRepository<Menu, Long> {
}
