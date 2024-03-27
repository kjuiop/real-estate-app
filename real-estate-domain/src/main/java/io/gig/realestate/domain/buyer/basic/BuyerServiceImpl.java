package io.gig.realestate.domain.buyer.basic;

import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.buyer.basic.dto.BuyerForm;
import io.gig.realestate.domain.buyer.basic.dto.BuyerListDto;
import io.gig.realestate.domain.buyer.basic.dto.BuyerSearchDto;
import io.gig.realestate.domain.buyer.detail.BuyerDetailService;
import io.gig.realestate.domain.buyer.basic.dto.BuyerDetailDto;
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
            List<String> purposeCdName = new ArrayList<>();
            if (StringUtils.hasText(dto.getPurposeCds())) {
                String[] purposeCds = dto.getPurposeCds().split(",");
                for (String code : purposeCds) {
                    purposeCdName.add(categoryService.getCategoryNameByCode(code));
                }
            }
            String gradeName = categoryService.getCategoryNameByCode(dto.getBuyerGradeCds());
            dto.setBuyerGradeName(gradeName);
            dto.setPurposeName(purposeCdName);
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
}
