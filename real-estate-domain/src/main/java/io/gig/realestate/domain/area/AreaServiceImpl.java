package io.gig.realestate.domain.area;

import io.gig.realestate.domain.area.dto.AreaListDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/10/03
 */
@Service
@RequiredArgsConstructor
public class AreaServiceImpl implements AreaService {

    private final AreaReader areaReader;
    private final AreaStore areaStore;

    @Override
    @Transactional(readOnly = true)
    public List<AreaListDto> getParentAreaList() {
        return areaReader.getParentAreaList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AreaListDto> getAreaListByParentId(Long areaId) {
        return areaReader.getAreaListByParentId(areaId);
    }

    @Override
    @Transactional
    public void createByExcelData(MultipartFile file) throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename()); // 3
        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            throw new IOException("엑셀파일만 업로드 해주세요.");
        }

        Workbook workbook = null;
        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        }

        Sheet worksheet = workbook.getSheetAt(0);

        List<Area> areaList = new ArrayList<>();
        Area sidoArea = null;
        Area gunguArea = null;
        Area dongArea = null;
        int sidoSortOrder = 0;
        int gunguSortOrder = 0;
        int dongSortOrder = 0;
        int riSortOrder = 0;
        for (int j=1; j< worksheet.getPhysicalNumberOfRows(); j++) {
            Row row = worksheet.getRow(j);
            String legalAddressCode = row.getCell(0).getStringCellValue();
            String sido = row.getCell(1).getStringCellValue();
            String gungu = "";
            String name = "";
            int sortOder = 0;
            int level = 1;

            if (row.getCell(2) == null) {
                name = sido;
                sortOder = sidoSortOrder;
            }

            if (row.getCell(2) != null) {
                gungu = row.getCell(2).getStringCellValue();
                name = gungu;
                sortOder = gunguSortOrder;
                level = 2;
            }
            String dong = "";
            if (row.getCell(3) != null) {
                dong = row.getCell(3).getStringCellValue();
                name = dong;
                sortOder = dongSortOrder;
                level = 3;
            }
            String ri = "";
            if (row.getCell(4) != null) {
                ri = row.getCell(4).getStringCellValue();
                name = ri;
                sortOder = riSortOrder;
                level = 4;
            }
            String createdAt = "";
            if (row.getCell(5) != null) {
                createdAt = row.getCell(5).getStringCellValue();
            }
            String canceledAt = "";
            if (row.getCell(6) != null) {
                canceledAt = row.getCell(6).getStringCellValue();
            }

            // 시도
            Area area = Area.create(legalAddressCode, name, sortOder, legalAddressCode, sido, gungu, dong, ri, createdAt, canceledAt, level);
            if (level == 1) {
                //TODO gugun 이 없을 때 데이터 싱크 안맞음
                //TODO contains 적용
                sidoArea = area;
                sidoSortOrder++;
            } else if (level == 2) {
                //TODO gugun 이 없을 때 데이터 싱크 안맞음
                //TODO contains 적용
                gunguArea = area;
                gunguSortOrder++;
                area.addParent(sidoArea);
            } else if (level == 3) {
                //TODO gugun 이 없을 때 데이터 싱크 안맞음
                //TODO contains 적용
                area.addParent(gunguArea);
                dongSortOrder++;
                dongArea = area;
            } else {
                riSortOrder++;
                area.addParent(dongArea);
            }
            areaList.add(area);
        }

        areaStore.storeAll(areaList);
    }

    public boolean isSkipData(String input) {
        String substring = input.substring(2, 5);
        if (substring.equals("105")) {
            return false;
        }

        return true;
    }
}
