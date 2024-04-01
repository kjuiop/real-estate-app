package io.gig.realestate.domain.buyer.basic;

import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.buyer.basic.dto.*;
import io.gig.realestate.domain.buyer.detail.BuyerDetailService;
import io.gig.realestate.domain.buyer.detail.dto.BuyerDetailUpdateForm;
import io.gig.realestate.domain.category.Category;
import io.gig.realestate.domain.category.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

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
    private final CategoryService categoryService;

    @Override
    @Transactional(readOnly = true)
    public Page<BuyerListDto> getBuyerPageListBySearch(BuyerSearchDto condition) {
        Page<BuyerListDto> content = buyerReader.getBuyerPageListBySearch(condition);
        for (BuyerListDto dto : content) {
            dto.setBuyerGradeName(categoryService.getCategoryNameByCode(dto.getBuyerGradeCds()));
            dto.setPurposeName(convertCdToNames(dto.getPurposeCds()));
            dto.convertSalePriceIntValue(dto.getSalePrice());
        }
        return content;
    }

    @Override
    @Transactional(readOnly = true)
    public BuyerDetailDto getBuyerDetail(Long buyerId) {
        return buyerReader.getBuyerDetail(buyerId);
    }

    @Override
    @Transactional(readOnly = true)
    public BuyerModalDto getBuyerDetailModal(Long buyerId, LoginUser loginUser) {
        BuyerDetailDto dto = buyerReader.getBuyerDetail(buyerId);
        dto.setPurposeNameStr(convertCdToNameStr(dto.getPurposeCds()));
        dto.setLoanCharacterNames(convertCdToNameStr(dto.getLoanCharacterCds()));
        dto.setPreferBuildingName(convertCdToNameStr(dto.getPreferBuildingCds()));
        dto.setInvestmentTimingNames(convertCdToNameStr(dto.getInvestmentTimingCds()));
        return new BuyerModalDto(dto);
    }

    @Override
    @Transactional
    public Long create(BuyerForm createForm, LoginUser loginUser) {
        Buyer buyer = Buyer.create(createForm, loginUser.getLoginUser());
        return buyerStore.store(buyer).getId();
    }

    @Override
    @Transactional
    public Long update(BuyerForm updateForm, LoginUser loginUser) {
        Buyer buyer = buyerReader.getBuyerById(updateForm.getBuyerId());
        buyer.update(updateForm, loginUser);
        return buyerStore.store(buyer).getId();
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
