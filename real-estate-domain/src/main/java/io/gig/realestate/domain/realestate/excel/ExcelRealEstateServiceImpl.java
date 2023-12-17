package io.gig.realestate.domain.realestate.excel;

import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.event.RealEstateEvent;
import io.gig.realestate.domain.realestate.excel.dto.ExcelRealEstateDto;
import io.gig.realestate.domain.realestate.excel.dto.ExcelUploadCheckDto;
import io.gig.realestate.domain.realestate.excel.types.UploadStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/12/02
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ExcelRealEstateServiceImpl implements ExcelRealEstateService {

    private final ExcelRealEstateReader excelRealEstateReader;
    private final ExcelRealEstateStore excelRealEstateStore;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public int createAndPublish(List<ExcelRealEstateDto> excelRealEstateList, String username) {

        List<RealEstateEvent> eventList = new ArrayList<>();
        List<ExcelRealEstate> data = new ArrayList<>();

        int successCnt = 0;
        for (ExcelRealEstateDto dto : excelRealEstateList) {

            if (dto.getFailYn() == YnType.Y) {
                ExcelRealEstate excelRealEstate = ExcelRealEstate.failData(dto, username);
                data.add(excelRealEstate);
                continue;
            }

            ExcelRealEstate excelRealEstate = ExcelRealEstate.excelCreate(dto, username);
            excelRealEstate.isPublish();
            data.add(excelRealEstate);

            RealEstateEvent event = new RealEstateEvent(excelRealEstate, "[excel-parser]-" + dto.getAddress());
            eventList.add(event);
            successCnt++;
        }

        excelRealEstateStore.storeAll(data);

        int i=0;
        for (RealEstateEvent event : eventList) {
            i++;
            event.setTimeSleep(i);
            eventPublisher.publishEvent(event);
        }

        return calculateTimeout(successCnt, excelRealEstateList.size());
    }

    @Override
    @Transactional(readOnly = true)
    public ExcelRealEstate findById(Long id) {
        return excelRealEstateReader.findById(id);
    }

    @Override
    public ExcelUploadCheckDto checkExcelUploadProgress(String uploadId) {
        YnType isComplete = YnType.Y;

        List<ExcelRealEstateDto> excelData = new ArrayList<>();
        List<ExcelRealEstate> excelRealEstates = excelRealEstateReader.findByUploadId(uploadId);
        for (ExcelRealEstate data : excelRealEstates) {
            if (data.getUploadStatus() == UploadStatus.PENDING) {
                isComplete = YnType.N;
            }
            excelData.add(ExcelRealEstateDto.entityToDto(data));
        }

        return ExcelUploadCheckDto.uploadCheck(isComplete, excelData);
    }

    private int calculateTimeout(int successCnt, int dataSize) {
        int timeoutLimit = 2 * 1000 * successCnt;
        timeoutLimit += 500 * dataSize;
        if (timeoutLimit <= 6000) {
            timeoutLimit = 6000;
        }
        if (timeoutLimit >= 180000) {
            timeoutLimit = 180000;
        }

        return timeoutLimit;
    }
}
