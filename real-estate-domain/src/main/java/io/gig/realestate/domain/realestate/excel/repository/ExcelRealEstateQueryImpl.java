package io.gig.realestate.domain.realestate.excel.repository;

import io.gig.realestate.domain.exception.NotFoundException;
import io.gig.realestate.domain.realestate.excel.ExcelRealEstate;
import io.gig.realestate.domain.realestate.excel.ExcelRealEstateReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2023/12/06
 */
@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ExcelRealEstateQueryImpl implements ExcelRealEstateReader {

    private final ExcelRealEstateQueryRepository excelRealEstateQueryRepository;

    @Override
    public ExcelRealEstate findById(Long id) {
        Optional<ExcelRealEstate> findExcelRealEstate = excelRealEstateQueryRepository.findById(id);
        if (findExcelRealEstate.isEmpty()) {
            throw new NotFoundException("excel real estate id : " + id);
        }

        return findExcelRealEstate.get();
    }

    @Override
    public List<ExcelRealEstate> findByUploadId(String uploadId) {
        return excelRealEstateQueryRepository.findAllByUploadIdOrderByRowIndexAsc(uploadId);
    }
}
