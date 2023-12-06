package io.gig.realestate.domain.realestate.excel;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/12/06
 */
public interface ExcelRealEstateReader {
    ExcelRealEstate findById(Long id);

    List<ExcelRealEstate> findByUploadId(String uploadId);
}
