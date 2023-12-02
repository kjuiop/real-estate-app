package io.gig.realestate.domain.realestate.excel.repository;

import io.gig.realestate.domain.realestate.excel.ExcelRealEstate;
import io.gig.realestate.domain.realestate.excel.ExcelRealEstateStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/12/02
 */
@Component
@Transactional
@RequiredArgsConstructor
public class ExcelRealEstateStoreImpl implements ExcelRealEstateStore {

    private final ExcelRealEstateStoreRepository excelRealEstateStoreRepository;

    @Override
    public void storeAll(List<ExcelRealEstate> data) {
        excelRealEstateStoreRepository.saveAll(data);
    }

    @Override
    public void store(ExcelRealEstate data) {
        excelRealEstateStoreRepository.save(data);
    }
}
