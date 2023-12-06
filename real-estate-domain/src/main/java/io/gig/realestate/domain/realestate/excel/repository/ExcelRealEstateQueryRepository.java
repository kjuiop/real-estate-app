package io.gig.realestate.domain.realestate.excel.repository;

import io.gig.realestate.domain.realestate.excel.ExcelRealEstate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/12/06
 */
@Repository
public interface ExcelRealEstateQueryRepository extends JpaRepository<ExcelRealEstate, Long> {

    List<ExcelRealEstate> findAllByUploadIdOrderByRowIndexAsc(String uploadId);
}
