package io.gig.realestate.domain.buyer;

import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.buyer.dto.*;
import io.gig.realestate.domain.category.Category;
import io.gig.realestate.domain.category.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
        return buyerReader.getBuyerPageListBySearch(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public BuyerDetailDto getBuyerDetail(Long buyerId) {
        return buyerReader.getBuyerDetail(buyerId);
    }

    @Override
    @Transactional
    public Long create(BuyerCreateForm createForm, LoginUser loginUser) {
        Category processCd = categoryService.getCategoryById(createForm.getProcessCd());
        Category investCharacterCd = categoryService.getCategoryById(createForm.getInvestmentCharacterCd());
        Buyer buyer = Buyer.create(createForm, processCd, loginUser.getLoginUser());
        BuyerDetail detail = BuyerDetail.create(createForm, processCd, investCharacterCd, buyer, loginUser.getLoginUser());
        buyer.addDetail(detail);
        return buyerStore.store(buyer).getId();
    }

    @Override
    @Transactional
    public Long update(BuyerDetailUpdateForm updateForm, LoginUser loginUser) {
        Category processCd = categoryService.getCategoryById(updateForm.getProcessCd());
        Category investCharacterCd = categoryService.getCategoryById(updateForm.getInvestmentCharacterCd());
        Buyer buyer = buyerReader.getBuyerById(updateForm.getBuyerId());
        Optional<BuyerDetail> findDetail = buyerReader.getBuyerDetailByIdAndProcessCd(updateForm.getBuyerId(), updateForm.getProcessCd());
        BuyerDetail detail;
        if (findDetail.isEmpty()) {
            detail = BuyerDetail.create(updateForm, processCd, investCharacterCd, buyer, loginUser.getLoginUser());
        } else {
            detail = findDetail.get();
            detail.update(updateForm, processCd, investCharacterCd, loginUser);
        }

        buyerStore.storeDetail(detail);
        return buyer.getId();
    }
}
