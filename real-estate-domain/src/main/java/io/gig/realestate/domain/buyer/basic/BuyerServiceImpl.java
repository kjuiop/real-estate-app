package io.gig.realestate.domain.buyer.basic;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.admin.AdministratorService;
import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.buyer.basic.dto.*;
import io.gig.realestate.domain.buyer.history.BuyerHistory;
import io.gig.realestate.domain.buyer.history.BuyerHistoryService;
import io.gig.realestate.domain.buyer.history.dto.HistoryForm;
import io.gig.realestate.domain.buyer.history.dto.HistoryListDto;
import io.gig.realestate.domain.buyer.manager.BuyerManager;
import io.gig.realestate.domain.buyer.manager.BuyerManagerService;
import io.gig.realestate.domain.buyer.manager.dto.BuyerManagerDto;
import io.gig.realestate.domain.buyer.maps.BuyerHistoryMap;
import io.gig.realestate.domain.buyer.maps.BuyerHistoryMapService;
import io.gig.realestate.domain.buyer.maps.dto.HistoryMapForm;
import io.gig.realestate.domain.buyer.maps.dto.HistoryMapListDto;
import io.gig.realestate.domain.buyer.realestate.HistoryRealEstate;
import io.gig.realestate.domain.category.CategoryService;
import io.gig.realestate.domain.category.dto.CategoryDto;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.notification.NotificationService;
import io.gig.realestate.domain.realestate.basic.RealEstate;
import io.gig.realestate.domain.realestate.basic.RealEstateService;
import io.gig.realestate.domain.role.dto.RoleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : JAKE
 * @date : 2024/02/24
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BuyerServiceImpl implements BuyerService {

    private final BuyerReader buyerReader;
    private final BuyerStore buyerStore;

    private final BuyerHistoryService historyService;
    private final BuyerHistoryMapService mapService;
    private final BuyerManagerService buyerManagerService;
    private final AdministratorService administratorService;
    private final RealEstateService realEstateService;
    private final CategoryService categoryService;
    private final NotificationService notificationService;

    @Override
    @Transactional(readOnly = true)
    public Page<BuyerListDto> getBuyerPageListBySearch(BuyerSearchDto condition, LoginUser loginUser) {

        Page<BuyerListDto> content = buyerReader.getBuyerPageListBySearch(condition, loginUser.getLoginUser());
        for (BuyerListDto dto : content) {
            dto.setBuyerGradeName(categoryService.getCategoryNameByCode(dto.getBuyerGradeCds()));
            dto.setPurposeName(convertCdToNames(dto.getPurposeCds()));
            dto.setHistoryMap(mapService.getHistoryMapByBuyerId(dto.getBuyerId()));
            dto.convertSalePriceIntValue(dto.getSalePrice());
            // 담당자 표시
        }
        return content;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BuyerDetailDto> getBuyerProcessingList() {
        return buyerReader.getBuyerProcessingList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BuyerListDto> getBuyerListByLoginUser(LoginUser loginUser) {
        return buyerReader.getBuyerListByLoginUserId(loginUser.getLoginUser());
    }

    @Override
    @Transactional(readOnly = true)
    public BuyerDetailDto getBuyerDetail(Long buyerId) {
        BuyerDetailDto detail = buyerReader.getBuyerDetail(buyerId);
        detail.setBuyerGradeName(categoryService.getCategoryNameByCode(detail.getBuyerGradeCds()));
        detail.setHistoryMap(mapService.getHistoryMapByBuyerId(detail.getBuyerId()));
        detail.convertSalePriceIntValue(detail.getSalePrice());
        return detail;
    }

    @Override
    @Transactional(readOnly = true)
    public Buyer getBuyerEntityByBuyerId(Long buyerId) {
        return buyerReader.getBuyerById(buyerId);
    }

    @Override
    @Transactional
    public Long create(BuyerForm createForm, LoginUser loginUser) {
        Administrator loginAdmin = loginUser.getLoginUser();
        Buyer buyer = Buyer.create(createForm, loginAdmin);
        List<CategoryDto> categories = categoryService.getChildrenCategoryDtosByCode("CD_PROCESS");
        for (CategoryDto dto : categories) {
            BuyerHistoryMap history = BuyerHistoryMap.create(dto, buyer, loginAdmin);
            buyer.getMaps().add(history);
        }

        if (createForm.getManagerIds() != null && !createForm.getManagerIds().contains(loginAdmin.getId())) {
            createForm.getManagerIds().add(loginAdmin.getId());
        }

        for (Long adminId : createForm.getManagerIds()) {
            Administrator manager = administratorService.getAdminById(adminId);
            BuyerManager buyerManager = BuyerManager.create(buyer, manager, loginAdmin);
            buyer.addManager(buyerManager);
        }

        Buyer savedBuyer = buyerStore.store(buyer);
        notificationService.sendBuyerCreateToManager(savedBuyer.getId(), savedBuyer.getCustomerName(), loginAdmin.getId(), createForm.getManagerIds());
        return savedBuyer.getId();
    }

    @Override
    @Transactional
    public Long update(BuyerForm updateForm, LoginUser loginUser) {
        Administrator loginAdmin = loginUser.getLoginUser();
        Buyer buyer = buyerReader.getBuyerById(updateForm.getBuyerId());
        buyer.update(updateForm, loginUser);

        for (BuyerManager bm : buyer.getManagers()) {
            boolean existsInManager = updateForm.getManagerIds().stream().anyMatch(id -> id.equals(bm.getAdmin().getId()));
            if (!existsInManager) {
                bm.delete();
            }
        }

        if (updateForm.getManagerIds() != null && !updateForm.getManagerIds().contains(loginAdmin.getId())) {
            updateForm.getManagerIds().add(loginAdmin.getId());
        }

        for (Long adminId : updateForm.getManagerIds()) {
            Administrator manager = administratorService.getAdminById(adminId);
            Optional<BuyerManager> findBuyerManager = buyerManagerService.getBuyerManager(buyer, manager);
            BuyerManager buyerManager;
            if (findBuyerManager.isPresent()) {
                buyerManager = findBuyerManager.get();
                buyerManager.update(buyer, manager, loginAdmin);
            } else {
                buyerManager = BuyerManager.create(buyer, manager, loginUser.getLoginUser());
                buyer.getManagers().add(buyerManager);
            }
        }

        Buyer savedBuyer = buyerStore.store(buyer);
        notificationService.sendBuyerUpdateToManager(savedBuyer.getId(), savedBuyer.getCustomerName(), loginAdmin.getId(), updateForm.getManagerIds());
        return savedBuyer.getId();
    }

    @Override
    @Transactional
    public Long delete(Long buyerId, LoginUser loginUser) {
        Administrator loginAdmin = loginUser.getLoginUser();
        Buyer buyer = buyerReader.getBuyerById(buyerId);
        buyer.delete(loginAdmin);
        return buyer.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public BuyerModalDto getBuyerDetailModal(Long buyerId, LoginUser loginUser) {
        BuyerDetailDto dto = buyerReader.getBuyerDetail(buyerId);
        dto.setPurposeNameStr(convertCdToNameStr(dto.getPurposeCds()));
        dto.setLoanCharacterNames(convertCdToNameStr(dto.getLoanCharacterCds()));
        dto.setPreferBuildingName(convertCdToNameStr(dto.getPreferBuildingCds()));
        dto.setInvestmentTimingNames(convertCdToNameStr(dto.getInvestmentTimingCds()));
        dto.setHistories(historyService.getHistoriesByBuyerId(buyerId));
        return new BuyerModalDto(dto);
    }

    @Override
    @Transactional
    public List<HistoryListDto> createHistory(Long buyerId, HistoryForm createForm, LoginUser loginUser) {
        Buyer buyer = buyerReader.getBuyerById(buyerId);
        BuyerHistory history = BuyerHistory.create(createForm, buyer, loginUser.getLoginUser());
        for (Long realEstateId : createForm.getRealEstateIds()) {
            RealEstate realEstate = realEstateService.getRealEstateById(realEstateId);
            HistoryRealEstate historyRealEstate = HistoryRealEstate.create(realEstate, history, loginUser.getLoginUser());
            history.addHistoryRealEstate(historyRealEstate);
        }
        buyer.addHistory(history);

        for (BuyerHistoryMap map : buyer.getMaps()) {
            if (createForm.getProcessName().equals(map.getProcessName())) {
                map.increaseHistoryCnt();
                break;
            }
        }

        buyerStore.store(buyer);
        return historyService.getHistoriesByBuyerId(buyerId);
    }

    @Override
    @Transactional
    public Long createHistoryMap(Long buyerId, HistoryMapForm createForm, LoginUser loginUser) {
        Buyer buyer = buyerReader.getBuyerById(buyerId);
        BuyerHistoryMap historyMap = BuyerHistoryMap.createCustomMap(buyer, createForm, loginUser.getLoginUser());
        buyer.getMaps().add(historyMap);
        return buyerStore.store(buyer).getId();
    }

    @Override
    @Transactional
    public Long changeCompleteType(Long buyerId, BuyerCompleteDto completeDto, LoginUser loginUser) {
        Buyer buyer = buyerReader.getBuyerById(buyerId);
        buyer.changeCompleteType(completeDto);
        return buyerStore.store(buyer).getId();
    }

    @Override
    public boolean checkIsBuyerManager(LoginUser loginUser, List<BuyerManagerDto> managers) {
        if (loginUser.isSuperAdmin()) {
            return true;
        }

        for (BuyerManagerDto manager : managers) {
            if (Objects.equals(manager.getAdminId(), loginUser.getId())) {
                return true;
            }
        }

        return false;
    }

    private List<String> convertCdToNames(String code) {
        if (!StringUtils.hasText(code)) {
            return new ArrayList<>();
        }
        List<String> names = new ArrayList<>();
        String[] arrays = code.split(",");
        for (String str : arrays) {
            names.add(categoryService.getCategoryNameByCode(str));
        }
        return names;
    }

    private String convertCdToNameStr(String code) {
        if (!StringUtils.hasText(code)) {
            return null;
        }
        StringBuilder names = new StringBuilder();
        String[] arrays = code.split(",");
        for (int i=0; i<arrays.length; i++) {
            names.append(categoryService.getCategoryNameByCode(arrays[i]));
            if (i < arrays.length-1) {
                names.append(",");
            }
        }
        return names.toString();
    }
}
