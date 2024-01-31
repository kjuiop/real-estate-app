package io.gig.realestate.domain.realestate.basic;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.admin.AdministratorService;
import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.area.Area;
import io.gig.realestate.domain.area.AreaService;
import io.gig.realestate.domain.category.Category;
import io.gig.realestate.domain.category.CategoryService;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.basic.dto.*;
import io.gig.realestate.domain.realestate.construct.ConstructInfo;
import io.gig.realestate.domain.realestate.construct.ConstructService;
import io.gig.realestate.domain.realestate.construct.dto.ConstructDataApiDto;
import io.gig.realestate.domain.realestate.construct.dto.ConstructFloorDataApiDto;
import io.gig.realestate.domain.realestate.construct.dto.FloorCreateForm;
import io.gig.realestate.domain.realestate.curltraffic.CurlTrafficLight;
import io.gig.realestate.domain.realestate.customer.CustomerInfo;
import io.gig.realestate.domain.realestate.customer.dto.CustomerCreateForm;
import io.gig.realestate.domain.realestate.excel.ExcelRealEstate;
import io.gig.realestate.domain.realestate.excel.ExcelRealEstateService;
import io.gig.realestate.domain.realestate.excel.dto.ExcelRealEstateDto;
import io.gig.realestate.domain.realestate.excel.dto.ExcelUploadDto;
import io.gig.realestate.domain.realestate.image.ImageInfo;
import io.gig.realestate.domain.realestate.image.dto.ImageCreateForm;
import io.gig.realestate.domain.realestate.land.LandInfo;
import io.gig.realestate.domain.realestate.land.LandReader;
import io.gig.realestate.domain.realestate.land.LandService;
import io.gig.realestate.domain.realestate.land.dto.LandDataApiDto;
import io.gig.realestate.domain.realestate.land.dto.LandInfoDto;
import io.gig.realestate.domain.realestate.landprice.LandPriceInfo;
import io.gig.realestate.domain.realestate.landprice.dto.LandPriceCreateForm;
import io.gig.realestate.domain.realestate.landusage.LandUsageInfo;
import io.gig.realestate.domain.realestate.landusage.LandUsageService;
import io.gig.realestate.domain.realestate.memo.MemoInfo;
import io.gig.realestate.domain.realestate.price.FloorPriceInfo;
import io.gig.realestate.domain.realestate.price.PriceInfo;
import io.gig.realestate.domain.realestate.price.PriceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
    private final LandUsageService landUsageService;
    private final ConstructService constructService;
    private final PriceService priceService;
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

        CurlTrafficLight trafficLight = CurlTrafficLight.initTrafficLight(newRealEstate);
        int landDataResCode = 200;
        LocalDateTime lastCurlLandApiAt = null;
        for (int i=0; i<createForm.getLandInfoList().size(); i++) {
            LandInfoDto dto = createForm.getLandInfoList().get(i);
            if (i == 0) {
                lastCurlLandApiAt = dto.getLastCurlApiAt();
            }
            if (dto.getResponseCode() != 200) {
                landDataResCode = dto.getResponseCode();
            }

            LandInfo landInfo = LandInfo.create(dto, newRealEstate);
            newRealEstate.addLandInfo(landInfo);
        }
        trafficLight.setLandDataApiResult(landDataResCode, lastCurlLandApiAt);


        LandUsageInfo landUsageInfo = LandUsageInfo.create(createForm.getLandUsageInfo(), newRealEstate, loginUser.getLoginUser());
        newRealEstate.addLandUsageInfo(landUsageInfo);
        trafficLight.setLandUsageDataApiResult(landUsageInfo.getResponseCode(), landUsageInfo.getLastCurlApiAt());

        PriceInfo priceInfo = PriceInfo.create(createForm.getPriceInfo(), newRealEstate);
        newRealEstate.addPriceInfo(priceInfo);

        int floorDataResCode = 200;
        LocalDateTime lastCurlFloorApiAt = null;
        for (int i=0; i<createForm.getFloorInfoList().size(); i++) {
            FloorCreateForm dto = createForm.getFloorInfoList().get(i);

            if (i == 0) {
                lastCurlFloorApiAt = dto.getLastCurlApiAt();
            }
            if (dto.getResponseCode() != 200) {
                floorDataResCode = dto.getResponseCode();
            }

            FloorPriceInfo floorInfo = FloorPriceInfo.create(dto, newRealEstate, i, loginUser.getLoginUser());
            newRealEstate.addFloorInfo(floorInfo);
        }
        trafficLight.setFloorDataApiResult(floorDataResCode, lastCurlFloorApiAt);

        ConstructInfo constructInfo = ConstructInfo.create(createForm.getConstructInfo(), newRealEstate, loginUser.getLoginUser());
        newRealEstate.addConstructInfo(constructInfo);
        trafficLight.setConstructDataApiResult(constructInfo.getResponseCode(), constructInfo.getLastCurlApiAt());

        for (CustomerCreateForm dto : createForm.getCustomerInfoList()) {
            CustomerInfo customerInfo = CustomerInfo.create(dto, newRealEstate);
            newRealEstate.addCustomerInfo(customerInfo);
        }

        for (ImageCreateForm dto : createForm.getSubImages()) {
            ImageInfo imageInfo = ImageInfo.create(dto, newRealEstate, loginUser.getLoginUser());
            newRealEstate.addImageInfo(imageInfo);
        }

        int landPriceResCode = 200;
        LocalDateTime landPriceLastCurlApiAt = null;
        for (int i=0; i<createForm.getLandPriceInfoList().size(); i++) {
            LandPriceCreateForm dto = createForm.getLandPriceInfoList().get(i);
            if (i==0) {
                landPriceLastCurlApiAt = dto.getLastCurlApiAt();
            }
            if (dto.getResponseCode() != 200) {
                landPriceResCode = dto.getResponseCode();
            }
            LandPriceInfo landPriceInfo = LandPriceInfo.create(dto, newRealEstate, loginUser.getLoginUser());
            newRealEstate.addLandPriceInfo(landPriceInfo);
        }
        trafficLight.setLandPriceDataApiResult(landPriceResCode, landPriceLastCurlApiAt);
        newRealEstate.addCurlTrafficInfo(trafficLight);
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

        CurlTrafficLight trafficLight;
        if (realEstate.getCurlTrafficInfoList().size() > 0) {
            trafficLight = realEstate.getCurlTrafficInfoList().get(0);
        } else {
            trafficLight = CurlTrafficLight.initTrafficLight(realEstate);
        }

        List<LandInfo> toRemoveLand = new ArrayList<>();
        for (LandInfo existingLandInfo : realEstate.getLandInfoList()) {
            boolean existsInUpdateLandForm = updateForm.getLandInfoList().stream()
                    .anyMatch(dto -> dto.getLandId() != null && dto.getLandId().equals(existingLandInfo.getId()));
            if (!existsInUpdateLandForm) {
                toRemoveLand.add(existingLandInfo);
            }
        }
        realEstate.getLandInfoList().removeAll(toRemoveLand);

        int landDataResCode = 0;
        LocalDateTime lastCurlLandApiAt = null;
        for (int i=0; i<updateForm.getLandInfoList().size(); i++) {
            LandInfoDto dto = updateForm.getLandInfoList().get(i);
            if (i == 0) {
                landDataResCode = dto.getResponseCode();
                lastCurlLandApiAt = dto.getLastCurlApiAt();
            }
            if (dto.getResponseCode() != 200) {
                landDataResCode = dto.getResponseCode();
            }

            LandInfo landInfo;
            if (dto.getLandId() != null)  {
                landInfo = landService.getLandInfoById(dto.getLandId());
                landInfo.update(dto);
            } else {
                landInfo = LandInfo.create(dto, realEstate);
                realEstate.addLandInfo(landInfo);
            }
        }
        trafficLight.setLandDataApiResult(landDataResCode, lastCurlLandApiAt);

        LandUsageInfo landUsageInfo;
        if (updateForm.getLandUsageInfo() != null && updateForm.getLandUsageInfo().getLandUsageId() != null) {
            landUsageInfo = landUsageService.getLandUsageInfoById(updateForm.getLandUsageInfo().getLandUsageId());
            landUsageInfo.update(updateForm.getLandUsageInfo(), loginUser.getLoginUser());
        } else {
            landUsageInfo = LandUsageInfo.create(updateForm.getLandUsageInfo(), realEstate, loginUser.getLoginUser());
            realEstate.addLandUsageInfo(landUsageInfo);
        }
        trafficLight.setLandUsageDataApiResult(landUsageInfo.getResponseCode(), landUsageInfo.getLastCurlApiAt());

        PriceInfo priceInfo;
        if (updateForm.getPriceInfo() != null && updateForm.getPriceInfo().getPriceId() != null) {
            priceInfo = priceService.getPriceInfoByPriceId(updateForm.getPriceInfo().getPriceId());
            priceInfo.update(updateForm.getPriceInfo(), loginUser.getLoginUser());
        } else {
            priceInfo = PriceInfo.create(updateForm.getPriceInfo(), realEstate);
            realEstate.addPriceInfo(priceInfo);
        }

        List<FloorPriceInfo> toRemoveFloor = new ArrayList<>();
        for (FloorPriceInfo existingFloorInfo : realEstate.getFloorPriceInfo()) {
            boolean existsInUpdateFloorForm = updateForm.getFloorInfoList().stream()
                    .anyMatch(dto -> dto.getFloorId() != null && dto.getFloorId().equals(existingFloorInfo.getId()));
            if (!existsInUpdateFloorForm) {
                toRemoveFloor.add(existingFloorInfo);
            }
        }
        realEstate.getFloorPriceInfo().removeAll(toRemoveFloor);

        int floorDataResCode = 0;
        LocalDateTime lastCurlFloorApiAt = null;
        for (int i=0; i<updateForm.getFloorInfoList().size(); i++) {
            FloorCreateForm dto = updateForm.getFloorInfoList().get(i);
            if (i == 0) {
                floorDataResCode = dto.getResponseCode();
                lastCurlFloorApiAt = dto.getLastCurlApiAt();
            }
            if (dto.getResponseCode() != 200) {
                floorDataResCode = dto.getResponseCode();
            }

            FloorPriceInfo floorInfo;
            if (dto.getFloorId() != null) {
                floorInfo = constructService.getConstructFloorById(dto.getFloorId());
                floorInfo.update(dto, i, loginUser.getLoginUser());
            } else {
                floorInfo = FloorPriceInfo.create(dto, realEstate, i, loginUser.getLoginUser());
                realEstate.addFloorInfo(floorInfo);
            }
        }
        trafficLight.setFloorDataApiResult(floorDataResCode, lastCurlFloorApiAt);

        ConstructInfo constructInfo;
        if (updateForm.getConstructInfo() != null && updateForm.getConstructInfo().getConstructId() != null) {
            constructInfo = constructService.getConstructInfoById(updateForm.getConstructInfo().getConstructId());
            constructInfo.update(updateForm.getConstructInfo(), loginUser.getLoginUser());
        } else {
            constructInfo = ConstructInfo.create(updateForm.getConstructInfo(), realEstate, loginUser.getLoginUser());
            realEstate.addConstructInfo(constructInfo);
        }
        trafficLight.setConstructDataApiResult(constructInfo.getResponseCode(), constructInfo.getLastCurlApiAt());

        realEstate.getCustomerInfoList().clear();
        for (CustomerCreateForm dto : updateForm.getCustomerInfoList()) {
            CustomerInfo customerInfo = CustomerInfo.create(dto, realEstate);
            realEstate.addCustomerInfo(customerInfo);
        }

        realEstate.getSubImgInfoList().clear();
        String imageUrl = "";
        for (int i=0; i<updateForm.getSubImages().size(); i++) {
            ImageCreateForm dto = updateForm.getSubImages().get(i);
            if (i == 0) {
                imageUrl = dto.getFullPath();
            }
            ImageInfo imageInfo = ImageInfo.create(dto, realEstate, loginUser.getLoginUser());
            realEstate.addImageInfo(imageInfo);
        }
        realEstate.updateImageFullPath(imageUrl);

        int landPriceResCode = 0;
        LocalDateTime landPriceLastCurlApiAt = null;
        realEstate.getLandPriceInfoList().clear();
        for (int i=0; i<updateForm.getLandPriceInfoList().size(); i++) {
            LandPriceCreateForm dto = updateForm.getLandPriceInfoList().get(i);
            if (i==0) {
                landPriceResCode = dto.getResponseCode();
                landPriceLastCurlApiAt = dto.getLastCurlApiAt();
            }
            if (dto.getResponseCode() != 200) {
                landPriceResCode = dto.getResponseCode();
            }
            LandPriceInfo landPriceInfo = LandPriceInfo.create(dto, realEstate, loginUser.getLoginUser());
            realEstate.addLandPriceInfo(landPriceInfo);
        }
        trafficLight.setLandPriceDataApiResult(landPriceResCode, landPriceLastCurlApiAt);
        if (realEstate.getCurlTrafficInfoList().size() == 0) {
            realEstate.addCurlTrafficInfo(trafficLight);
        }
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
    public ExcelUploadDto excelUpload(MultipartFile file, String username) throws IOException {

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
        String uuid = UUID.randomUUID().toString().replace("-", "");

        String uploadId = generateUniqueIdentifier(uuid, uploadTime);
//        int timeoutLimit = 180 * 1000;

        for (int j=1; j< worksheet.getPhysicalNumberOfRows(); j++) {
            Row row = worksheet.getRow(j+1);
            String skipReason = "";
            if (row == null || row.getCell(0) == null) {
                continue;
            }
            String agentName = row.getCell(0, Row.CREATE_NULL_AS_BLANK).getStringCellValue();
            String sido = row.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue();
            String gungu = row.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue();
            String dong = row.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue();
            String bunJiGeneral = row.getCell(4, Row.CREATE_NULL_AS_BLANK).getStringCellValue();
            String bunJiMountain = row.getCell(5, Row.CREATE_NULL_AS_BLANK).getStringCellValue();
            double salePrice = 0;
            String salePriceStr = String.valueOf(row.getCell(6, Row.CREATE_NULL_AS_BLANK));
            if (isNumeric(salePriceStr)) {
                salePrice = Double.parseDouble(salePriceStr);
            }
            String processValue = row.getCell(7, Row.CREATE_NULL_AS_BLANK).getStringCellValue();
            String etcInfo = row.getCell(8, Row.CREATE_NULL_AS_BLANK).getStringCellValue();

            if (!StringUtils.hasText(sido) || !StringUtils.hasText(gungu) || !StringUtils.hasText(dong)) {
                break;
            }

            sido = sido.trim();
            gungu = gungu.trim();
            dong = dong.trim();
            bunJiGeneral = bunJiGeneral.trim();
            if (StringUtils.hasText(bunJiMountain)) {
                bunJiMountain = bunJiMountain.trim();
            }

            Optional<Area> findDong = areaService.getAreaLikeNameAndArea(dong, sido, gungu, dong);
            if (findDong.isEmpty()) {
                skipReason = "시군구 데이터가 올바르지 않습니다.";
                String address = sido + " " + gungu + " " + dong + " " + bunJiGeneral;
                ExcelRealEstateDto dto = ExcelRealEstateDto.excelFailResponse(uploadId, j, address, skipReason);
                excelRealEstateList.add(dto);
                continue;
            }

            if (!StringUtils.hasText(bunJiGeneral)) {
                skipReason = "지번 데이터가 올바르지 않습니다.";
                String address = sido + " " + gungu + " " + dong + " " + bunJiGeneral;
                ExcelRealEstateDto dto = ExcelRealEstateDto.excelFailResponse(uploadId, j, address, skipReason);
                excelRealEstateList.add(dto);
                continue;
            }

            StringBuilder cleanBunJiGeneral = new StringBuilder();
            String[] bunJiArray = bunJiGeneral.split(",");
            for (int i=0; i<bunJiArray.length; i++) {
                if (!StringUtils.hasText(bunJiArray[i])) {
                    skipReason = "지번 데이터가 올바르지 않습니다.";
                    String address = sido + " " + gungu + " " + dong + " " + bunJiGeneral;
                    ExcelRealEstateDto dto = ExcelRealEstateDto.excelFailResponse(uploadId, j, address, skipReason);
                    excelRealEstateList.add(dto);
                    continue;
                }
                bunJiArray[i] = bunJiArray[i].replaceAll(" ", "");
                String regex = "[0-9-]+";
                if (!bunJiArray[i].matches(regex)) {
                    skipReason = "지번 데이터가 올바르지 않습니다.";
                    String address = sido + " " + gungu + " " + dong + " " + bunJiGeneral;
                    ExcelRealEstateDto dto = ExcelRealEstateDto.excelFailResponse(uploadId, j, address, skipReason);
                    excelRealEstateList.add(dto);
                    continue;
                }

                cleanBunJiGeneral.append(bunJiArray[i]);
                if (i != bunJiArray.length-1) {
                    cleanBunJiGeneral.append(",");
                }
            }
            String representBunJi = bunJiArray[0];


            StringBuilder cleanBunJiMountain = new StringBuilder();
            if (StringUtils.hasText(bunJiMountain)) {
                String[] bunJiMountainArray = bunJiMountain.split(",");
                for (int i=0; i<bunJiMountainArray.length; i++) {
                    if (!StringUtils.hasText(bunJiMountainArray[i])) {
                        skipReason = "지번 데이터가 올바르지 않습니다.";
                        String address = sido + " " + gungu + " " + dong + " " + bunJiMountain;
                        ExcelRealEstateDto dto = ExcelRealEstateDto.excelFailResponse(uploadId, j, address, skipReason);
                        excelRealEstateList.add(dto);
                        continue;
                    }
                    bunJiMountainArray[i] = bunJiMountainArray[i].replaceAll(" ", "");
                    String regex = "[0-9-]+";
                    if (!bunJiMountainArray[i].matches(regex)) {
                        skipReason = "지번 데이터가 올바르지 않습니다.";
                        String address = sido + " " + gungu + " " + dong + " " + bunJiMountain;
                        ExcelRealEstateDto dto = ExcelRealEstateDto.excelFailResponse(uploadId, j, address, skipReason);
                        excelRealEstateList.add(dto);
                        continue;
                    }

                    cleanBunJiMountain.append(bunJiMountainArray[i]);
                    if (i != bunJiMountainArray.length-1) {
                        cleanBunJiMountain.append(",");
                    }
                }
            }

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
                ExcelRealEstateDto dto = ExcelRealEstateDto.excelFailResponse(uploadId, j, address, skipReason);
                excelRealEstateList.add(dto);
                continue;
            }


            if (salePrice > 0) {
                salePrice = salePrice / 10000000;
            }

            ExcelRealEstateDto dto = ExcelRealEstateDto.excelCreate(uploadId, j, legalCode, agentName, address, sido, gungu, dong, cleanBunJiGeneral.toString(), cleanBunJiMountain.toString(), bun, ji, salePrice, processValue, etcInfo);
            excelRealEstateList.add(dto);
        }

        int timeout = excelRealEstateService.createAndPublish(excelRealEstateList, username);
        return new ExcelUploadDto(excelRealEstateList, timeout);
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
        Category usageType = categoryService.getCategoryByCode("CD_USAGE_01_01");
        RealEstate newRealEstate = RealEstate.createByExcelUpload(data.getAgentName(), data.getAddress(), data.getLegalCode(), data.getBun(), data.getJi(), data.getProcessType(), data.getCharacterInfo(), usageType, loginUser);

        if (!StringUtils.hasText(data.getBunJiGeneral())) {
            return;
        }

        int totalLndpclArByPyung = 0;
        String[] bunJiGeneralList = data.getBunJiGeneral().split(",");
        for (String bunji : bunJiGeneralList) {
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

            String address = data.getSido() + " " + data.getGungu() + " " + data.getDong() + " " + bun;
            if (StringUtils.hasText(ji)) {
                address += "-" + ji;
            }


            LandDataApiDto dto = landService.getLandPublicInfo(data.getLegalCode(), landType, bun, ji);
            if (dto != null) {
                LandInfo landInfo = LandInfo.createByExcelUpload(dto, address, newRealEstate);
                newRealEstate.addLandInfo(landInfo);

                totalLndpclArByPyung += landInfo.getLndpclArByPyung();
            }
        }
        String[] bunJiMountainList = data.getBunJiMountain().split(",");
        if (bunJiMountainList.length > 0) {
            for (String bunji : bunJiMountainList) {
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

                String address = data.getSido() + " " + data.getGungu() + " " + data.getDong() + " " + bun;
                if (StringUtils.hasText(ji)) {
                    address += "-" + ji;
                }

                LandDataApiDto dto = landService.getLandPublicInfo(data.getLegalCode(), "mountain", bun, ji);
                if (dto != null) {
                    LandInfo landInfo = LandInfo.createByExcelUpload(dto, address, newRealEstate);
                    newRealEstate.addLandInfo(landInfo);

                    totalLndpclArByPyung += landInfo.getLndpclArByPyung();
                }
            }
        }


        int totalTotAreaByPyung = 0;
        ConstructDataApiDto constructDto = constructService.getConstructInfo(data.getLegalCode(), landType, data.getBun(), data.getJi());
        if (constructDto != null) {
            ConstructInfo constructInfo = ConstructInfo.createByExcelUpload(constructDto, newRealEstate);
            newRealEstate.addConstructInfo(constructInfo);

            totalTotAreaByPyung += constructInfo.getTotArea();
        }

        PriceInfo priceInfo = PriceInfo.createByUpload(data.getSalePrice(), newRealEstate);
        priceInfo.calculatePyung(priceInfo.getSalePrice(), totalLndpclArByPyung, totalTotAreaByPyung);
        newRealEstate.addPriceInfo(priceInfo);

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

    @Override
    @Transactional(readOnly = true)
    public List<CoordinateDto> getCoordinateList(RealEstateSearchDto condition) {
        return realEstateReader.getCoordinateList(condition);
    }

    private String generateUniqueIdentifier(String uuid, String uploadTime) {
        // 파일명, 사용자명, 업로드 시점, UUID를 합쳐서 식별자 생성
        return uuid + "_" + uploadTime;
    }

    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;  // 숫자로 변환할 수 없는 경우
        }
        return true;
    }

}
