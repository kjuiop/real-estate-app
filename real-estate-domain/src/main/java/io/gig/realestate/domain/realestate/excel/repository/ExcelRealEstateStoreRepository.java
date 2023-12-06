package io.gig.realestate.domain.realestate.excel.repository;

import io.gig.realestate.domain.realestate.excel.ExcelRealEstate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : JAKE
 * @date : 2023/12/02
 */
public interface ExcelRealEstateStoreRepository extends JpaRepository<ExcelRealEstate, Long>  {
}
