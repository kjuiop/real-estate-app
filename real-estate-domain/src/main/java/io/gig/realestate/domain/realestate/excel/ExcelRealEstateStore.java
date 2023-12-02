package io.gig.realestate.domain.realestate.excel;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/12/02
 */
public interface ExcelRealEstateStore {
    void storeAll(List<ExcelRealEstate> data);

    void store(ExcelRealEstate data);
}
