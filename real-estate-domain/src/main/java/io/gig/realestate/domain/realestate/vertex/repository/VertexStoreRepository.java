package io.gig.realestate.domain.realestate.vertex.repository;

import io.gig.realestate.domain.realestate.vertex.Vertex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : JAKE
 * @date : 2023/12/26
 */
@Repository
public interface VertexStoreRepository extends JpaRepository<Vertex, Long> {
}
