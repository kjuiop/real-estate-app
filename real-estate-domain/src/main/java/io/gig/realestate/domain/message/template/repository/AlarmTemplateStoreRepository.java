package io.gig.realestate.domain.message.template.repository;

import io.gig.realestate.domain.message.template.AlarmTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : JAKE
 * @date : 2024/03/02
 */
@Repository
public interface AlarmTemplateStoreRepository extends JpaRepository<AlarmTemplate, Long> {
}
