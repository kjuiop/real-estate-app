package io.gig.realestate.domain.realestate.excel;

import io.gig.realestate.domain.admin.AdministratorRole;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.basic.RealEstate;
import io.gig.realestate.domain.realestate.event.RealEstateEvent;
import io.gig.realestate.domain.realestate.excel.dto.ExcelRealEstateDto;
import io.gig.realestate.domain.realestate.excel.dto.ExcelUploadCheckDto;
import io.gig.realestate.domain.realestate.excel.types.UploadStatus;
import io.gig.realestate.domain.role.dto.RoleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public void createAndPublish(List<ExcelRealEstateDto> excelRealEstateList, String username) {

        List<RealEstateEvent> eventList = new ArrayList<>();
        List<ExcelRealEstate> data = new ArrayList<>();
        for (ExcelRealEstateDto dto : excelRealEstateList) {

            ExcelRealEstate excelRealEstate;

            if (dto.getFailYn() == YnType.Y) {
                excelRealEstate = ExcelRealEstate.failData(dto, username);
            } else {
                excelRealEstate = ExcelRealEstate.excelCreate(dto, username);
            }

            excelRealEstate.isPublish();
            data.add(excelRealEstate);

            RealEstateEvent event = new RealEstateEvent(excelRealEstate, "[excel-parser]-" + dto.getAddress());
            eventList.add(event);
        }

        excelRealEstateStore.storeAll(data);

        int i=0;
        for (RealEstateEvent event : eventList) {
            i++;
            event.setTimeSleep(i);
            eventPublisher.publishEvent(event);
        }
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
}
