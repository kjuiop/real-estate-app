package io.gig.realestate.domain.realestate.basic;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.admin.AdministratorService;
import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.area.Area;
import io.gig.realestate.domain.area.AreaService;
import io.gig.realestate.domain.area.dto.AreaListDto;
import io.gig.realestate.domain.category.Category;
import io.gig.realestate.domain.category.CategoryService;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.basic.dto.*;
import io.gig.realestate.domain.realestate.construct.ConstructInfo;
import io.gig.realestate.domain.realestate.construct.ConstructService;
import io.gig.realestate.domain.realestate.construct.dto.ConstructDataApiDto;
import io.gig.realestate.domain.realestate.construct.dto.ConstructFloorDataApiDto;
import io.gig.realestate.domain.realestate.construct.dto.FloorCreateForm;
import io.gig.realestate.domain.realestate.customer.CustomerInfo;
import io.gig.realestate.domain.realestate.customer.dto.CustomerCreateForm;
import io.gig.realestate.domain.realestate.excel.ExcelRealEstate;
import io.gig.realestate.domain.realestate.excel.ExcelRealEstateService;
import io.gig.realestate.domain.realestate.excel.dto.ExcelRealEstateDto;
import io.gig.realestate.domain.realestate.image.ImageInfo;
import io.gig.realestate.domain.realestate.image.dto.ImageCreateForm;
import io.gig.realestate.domain.realestate.land.LandInfo;
import io.gig.realestate.domain.realestate.land.LandService;
import io.gig.realestate.domain.realestate.land.dto.LandDataApiDto;
import io.gig.realestate.domain.realestate.land.dto.LandInfoDto;
import io.gig.realestate.domain.realestate.memo.MemoInfo;
import io.gig.realestate.domain.realestate.price.FloorPriceInfo;
import io.gig.realestate.domain.realestate.price.PriceInfo;
import io.gig.realestate.domain.realestate.print.PrintInfo;
import io.gig.realestate.domain.realestate.print.dto.PrintCreateForm;
import io.gig.realestate.domain.realestate.print.repository.PrintStoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.format.CellFormatType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author : JAKE
 * @date : 2023/09/18
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RealEstateServiceImpl implements RealEstateService {

    private static Map<String, List<Long>> searchIdsMap = new HashMap<>();

    private final AdministratorService administratorService;
    private final CategoryService categoryService;

    private final RealEstateReader realEstateReader;
    private final RealEstateStore realEstateStore;

    private final AreaService areaService;
    private final LandService landService;
    private final ConstructService constructService;
    private final ExcelRealEstateService excelRealEstateService;

    @Override
    @Transactional(readOnly = true)
    public Page<RealEstateListDto> getRealEstatePageListBySearch(String sessionId, RealEstateSearchDto searchDto) {
        List<Long> searchIds = realEstateReader.getRealEstateIdsBySearch(searchDto);
        searchIdsMap.put(sessionId, searchIds);
        return realEstateReader.getRealEstatePageListBySearch(searchDto);
    }

    @Override
    @Transactional(readOnly = true)
    public RealEstateDetailDto getDetail(String sessionId, Long realEstateId) {
        RealEstateDetailDto detail = realEstateReader.getRealEstateDetail(realEstateId);
        List<Long> searchIds = searchIdsMap.get(sessionId);
        if (searchIds == null || searchIds.size() == 0) {
            return detail;
        }

        int currentIndex = searchIds.indexOf(realEstateId);
        if (currentIndex -1 >= 0) {
            detail.setPrevId(searchIds.get(currentIndex - 1));
        }
        if (currentIndex + 1 <= searchIds.size() - 1) {
            detail.setNextId(searchIds.get(currentIndex + 1));
        }
        return detail;
    }

    @Override
    @Transactional(readOnly = true)
    public RealEstateDetailAllDto getDetailAllInfo(Long realEstateId) {
        return realEstateReader.getRealEstateDetailAllInfo(realEstateId);
    }

    @Override
    @Transactional
    public Long create(RealEstateCreateForm createForm, LoginUser loginUser) {
        Administrator manager = administratorService.getAdminEntityByUsername(createForm.getManagerUsername());
        RealEstate newRealEstate;
        if (createForm.getUsageTypeId() != null) {
            Category usageType = categoryService.getCategoryById(createForm.getUsageTypeId());
            newRealEstate = RealEstate.createWithUsageType(createForm, manager, usageType, loginUser.getLoginUser());
        } else {
            newRealEstate = RealEstate.create(createForm, manager, loginUser.getLoginUser());
        }

        for (LandInfoDto dto : createForm.getLandInfoList()) {
            LandInfo landInfo = LandInfo.create(dto, newRealEstate);
            newRealEstate.addLandInfo(landInfo);
        }

        PriceInfo priceInfo = PriceInfo.create(createForm.getPriceInfo(), newRealEstate);
        newRealEstate.addPriceInfo(priceInfo);

        for (FloorCreateForm dto : createForm.getFloorInfoList()) {
            FloorPriceInfo floorInfo = FloorPriceInfo.create(dto, newRealEstate);
            newRealEstate.addFloorInfo(floorInfo);
        }

        ConstructInfo constructInfo = ConstructInfo.create(createForm.getConstructInfo(), newRealEstate);
        newRealEstate.addConstructInfo(constructInfo);

        for (CustomerCreateForm dto : createForm.getCustomerInfoList()) {
            CustomerInfo customerInfo = CustomerInfo.create(dto, newRealEstate);
            newRealEstate.addCustomerInfo(customerInfo);
        }

        for (ImageCreateForm dto : createForm.getSubImages()) {
            ImageInfo imageInfo = ImageInfo.create(dto, newRealEstate, loginUser.getLoginUser());
            newRealEstate.addImageInfo(imageInfo);
        }

        return realEstateStore.store(newRealEstate).getId();
    }

    @Override
    @Transactional
    public Long update(RealEstateUpdateForm updateForm, LoginUser loginUser) {
        Administrator manager = administratorService.getAdminEntityByUsername(updateForm.getManagerUsername());
        Category usageType = null;
        if (updateForm.getUsageTypeId() != null) {
            usageType = categoryService.getCategoryById(updateForm.getUsageTypeId());
        }

        RealEstate realEstate = realEstateReader.getRealEstateById(updateForm.getRealEstateId());
        realEstate.update(updateForm, manager, usageType, loginUser.getLoginUser());

        realEstate.getLandInfoList().clear();
        for (LandInfoDto dto : updateForm.getLandInfoList()) {
            LandInfo landInfo = LandInfo.create(dto, realEstate);
            realEstate.addLandInfo(landInfo);
        }

        realEstate.getPriceInfoList().clear();
        PriceInfo priceInfo = PriceInfo.create(updateForm.getPriceInfo(), realEstate);
        realEstate.addPriceInfo(priceInfo);

        realEstate.getFloorPriceInfo().clear();
        for (FloorCreateForm dto : updateForm.getFloorInfoList()) {
            FloorPriceInfo floorInfo = FloorPriceInfo.create(dto, realEstate);
            realEstate.addFloorInfo(floorInfo);
        }

        realEstate.getConstructInfoList().clear();
        ConstructInfo constructInfo = ConstructInfo.create(updateForm.getConstructInfo(), realEstate);
        realEstate.addConstructInfo(constructInfo);

        realEstate.getCustomerInfoList().clear();
        for (CustomerCreateForm dto : updateForm.getCustomerInfoList()) {
            CustomerInfo customerInfo = CustomerInfo.create(dto, realEstate);
            realEstate.addCustomerInfo(customerInfo);
        }

        realEstate.getSubImgInfoList().clear();
        for (ImageCreateForm dto : updateForm.getSubImages()) {
            ImageInfo imageInfo = ImageInfo.create(dto, realEstate, loginUser.getLoginUser());
            realEstate.addImageInfo(imageInfo);
        }

        realEstate.getPrintInfoList().clear();
        PrintInfo printInfo = PrintInfo.create(updateForm.getPrintInfo(), realEstate);
        realEstate.addPrintInfo(printInfo);

        return realEstateStore.store(realEstate).getId();
    }

    @Override
    @Transactional
    public Long updateProcessStatus(RealEstateUpdateForm updateForm, LoginUser loginUser) {
        RealEstate realEstate = realEstateReader.getRealEstateById(updateForm.getRealEstateId());
        realEstate.updateProcessStatus(updateForm.getProcessType(), loginUser.getLoginUser());

        String memo = updateForm.getProcessType().getDescription() + "상태로 변경하였습니다.";
        MemoInfo newMemo = MemoInfo.create(memo, realEstate, loginUser.getLoginUser());
        realEstate.addMemoInfo(newMemo);

        return realEstateStore.store(realEstate).getId();
    }

    @Override
    @Transactional
    public Long updateRStatus(StatusUpdateForm updateForm, LoginUser loginUser) {
        RealEstate realEstate = realEstateReader.getRealEstateById(updateForm.getRealEstateId());
        realEstate.updateRStatus(updateForm.getRYn());
        String status = YnType.Y == updateForm.getRYn() ? "활성화" : "비활성화";
        String memo = "R 상태를 " + status + "하였습니다.";
        MemoInfo newMemo = MemoInfo.create(memo, realEstate, loginUser.getLoginUser());
        realEstate.addMemoInfo(newMemo);

        return realEstateStore.store(realEstate).getId();
    }

    @Override
    @Transactional
    public Long updateABStatus(StatusUpdateForm updateForm, LoginUser loginUser) {
        RealEstate realEstate = realEstateReader.getRealEstateById(updateForm.getRealEstateId());
        realEstate.updateABStatus(updateForm.getAbYn());
        String status = YnType.Y == updateForm.getAbYn() ? "활성화" : "비활성화";
        String memo = "A-B 상태를 " + status + "하였습니다.";
        MemoInfo newMemo = MemoInfo.create(memo, realEstate, loginUser.getLoginUser());
        realEstate.addMemoInfo(newMemo);
        return realEstateStore.store(realEstate).getId();
    }

    @Override
    @Transactional
    public boolean checkDuplicateAddress(String address, LoginUser loginUser) {
        return realEstateReader.isExistAddress(address);
    }

    @Override
    public List<ExcelRealEstateDto> excelUpload(MultipartFile file, String username) throws IOException {

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            throw new IOException("엑셀파일만 업로드 해주세요.");
        }

        Workbook workbook = null;
        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        }

        List<ExcelRealEstateDto> excelRealEstateList = new ArrayList<>();

        Sheet worksheet = workbook.getSheetAt(0);

        String uploadTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String uploadId = generateUniqueIdentifier(username, file.getOriginalFilename(), uploadTime);
        int timeoutLimit = worksheet.getPhysicalNumberOfRows() * 2000;

        for (int j=2; j< worksheet.getPhysicalNumberOfRows(); j++) {
            Row row = worksheet.getRow(j);
            String skipReason = "";
            if (row.getCell(0) == null) {
                continue;
            }
            String agentName = row.getCell(0, Row.CREATE_NULL_AS_BLANK).getStringCellValue();
            String sido = row.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue();
            String gungu = row.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue();
            String dong = row.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue();
            String bunJi = row.getCell(4, Row.CREATE_NULL_AS_BLANK).getStringCellValue();

            if (!StringUtils.hasText(sido) || !StringUtils.hasText(gungu) || !StringUtils.hasText(dong)) {
                break;
            }

            sido = sido.trim();
            gungu = gungu.trim();
            dong = dong.trim();
            bunJi = bunJi.trim();

            Optional<Area> findDong = areaService.getAreaLikeNameAndArea(dong, sido, gungu, dong);
            if (findDong.isEmpty()) {
                skipReason = "시군구 데이터가 올바르지 않습니다.";
                String address = sido + " " + gungu + " " + dong + " " + bunJi;
                ExcelRealEstateDto dto = ExcelRealEstateDto.excelFailResponse(uploadId, timeoutLimit, j-1, address, skipReason);
                excelRealEstateList.add(dto);
                continue;
            }

            if (!StringUtils.hasText(bunJi)) {
                skipReason = "지번 데이터가 올바르지 않습니다.";
                String address = sido + " " + gungu + " " + dong + " " + bunJi;
                ExcelRealEstateDto dto = ExcelRealEstateDto.excelFailResponse(uploadId, timeoutLimit, j-1, address, skipReason);
                excelRealEstateList.add(dto);
                continue;
            }

            StringBuilder cleanBunJi = new StringBuilder();
            String[] bunJiArray = bunJi.split(",");
            for (int i=0; i<bunJiArray.length; i++) {
                if (!StringUtils.hasText(bunJiArray[i])) {
                    skipReason = "지번 데이터가 올바르지 않습니다.";
                    String address = sido + " " + gungu + " " + dong + " " + bunJi;
                    ExcelRealEstateDto dto = ExcelRealEstateDto.excelFailResponse(uploadId, timeoutLimit, j-1, address, skipReason);
                    excelRealEstateList.add(dto);
                    continue;
                }
                bunJiArray[i] = bunJiArray[i].replaceAll(" ", "");
                String regex = "[0-9-]+";
                if (!bunJiArray[i].matches(regex)) {
                    skipReason = "지번 데이터가 올바르지 않습니다.";
                    String address = sido + " " + gungu + " " + dong + " " + bunJi;
                    ExcelRealEstateDto dto = ExcelRealEstateDto.excelFailResponse(uploadId, timeoutLimit, j-1, address, skipReason);
                    excelRealEstateList.add(dto);
                    continue;
                }

                cleanBunJi.append(bunJiArray[i]);
                if (i != bunJiArray.length-1) {
                    cleanBunJi.append(",");
                }
            }
            String representBunJi = bunJiArray[0];

            String bun = "";
            String ji = "";
            String[] strArray = representBunJi.split("-");
            if (strArray.length > 1) {
                bun = strArray[0];
                ji = strArray[1];
            } else {
                bun = strArray[0];
            }

            Area dongArea = findDong.get();
            String address = sido + " " + gungu + " " + dong + " " + bun;
            if (StringUtils.hasText(ji)) {
                address += "-" + ji;
            }

            String legalCode = dongArea.getLegalAddressCode();

            boolean isExist = realEstateReader.isExistLegalCodeAndBunJi(legalCode, bun, ji);
            if (isExist) {
                skipReason = "이미 등록된 매물 주소입니다.";
                ExcelRealEstateDto dto = ExcelRealEstateDto.excelFailResponse(uploadId, timeoutLimit, j-1, address, skipReason);
                excelRealEstateList.add(dto);
                continue;
            }

            double salePrice = row.getCell(5).getNumericCellValue();
            if (salePrice > 0) {
                salePrice = salePrice / 10000000;
            }

            ExcelRealEstateDto dto = ExcelRealEstateDto.excelCreate(uploadId, timeoutLimit, j-1, legalCode, agentName, address, sido, gungu, dong, cleanBunJi.toString(), bun, ji, salePrice);
            excelRealEstateList.add(dto);
        }

        excelRealEstateService.createAndPublish(excelRealEstateList, username);

        return excelRealEstateList;
    }

    @Override
    @Transactional
    public void createByExcelUpload(ExcelRealEstate data) throws IOException {
        log.info("address : " + data.getAddress());
        String landType = "general";

        ExcelRealEstate excelRealEstate = excelRealEstateService.findById(data.getId());
        if (excelRealEstate.getFailYn() == YnType.Y) {
            return;
        }

        Administrator loginUser = administratorService.getAdminEntityByUsername(data.getUsername());
        RealEstate newRealEstate = RealEstate.createByExcelUpload(data.getAgentName(), data.getAddress(), data.getLegalCode(), data.getBun(), data.getJi(), loginUser);

        PriceInfo priceInfo = PriceInfo.createByUpload(data.getSalePrice(), newRealEstate);
        newRealEstate.addPriceInfo(priceInfo);

        if (!StringUtils.hasText(data.getBunJiStr())) {
            return;
        }
        String[] bunJiList = data.getBunJiStr().split(",");
        for (String bunji : bunJiList) {
            String bun = "";
            String ji = "";
            String[] strArray = bunji.split("-");
            if (strArray.length > 1) {
                bun = strArray[0];
                ji = strArray[1];
            } else {
                bun = strArray[0];
            }

            if (!StringUtils.hasText(bun)) {
                continue;
            }

            LandDataApiDto dto = landService.getLandPublicInfo(data.getLegalCode(), landType, bun, ji);
            LandInfo landInfo = LandInfo.createByExcelUpload(dto, data.getAddress(), newRealEstate);
            newRealEstate.addLandInfo(landInfo);
        }

        ConstructDataApiDto constructDto = constructService.getConstructInfo(data.getLegalCode(), landType, data.getBun(), data.getJi());
        if (constructDto != null) {
            ConstructInfo constructInfo = ConstructInfo.createByExcelUpload(constructDto, newRealEstate);
            newRealEstate.addConstructInfo(constructInfo);
        }

        List<ConstructFloorDataApiDto> floorInfo = constructService.getConstructFloorInfo(data.getLegalCode(), landType, data.getBun(), data.getJi());
        if (floorInfo != null && floorInfo.size() > 0) {
            for (ConstructFloorDataApiDto dto : floorInfo) {
                FloorPriceInfo floorPriceInfo = FloorPriceInfo.createByExcelUpload(dto, newRealEstate);
                newRealEstate.addFloorInfo(floorPriceInfo);
            }
        }

        realEstateStore.store(newRealEstate);
        excelRealEstate.isComplete();
    }

    @Override
    @Transactional
    public Long getPrevRealEstateId(Long realEstateId) {
        return realEstateReader.getPrevRealEstateId(realEstateId);
    }

    @Override
    @Transactional
    public Long getNextRealEstateId(Long realEstateId) {
        return realEstateReader.getNextRealEstateId(realEstateId);
    }

    private String generateUniqueIdentifier(String username, String fileName, String uploadTime) {
        // 파일명, 사용자명, 업로드 시점, UUID를 합쳐서 식별자 생성
        return fileName + "_" + username + "_" + uploadTime;
    }
}
