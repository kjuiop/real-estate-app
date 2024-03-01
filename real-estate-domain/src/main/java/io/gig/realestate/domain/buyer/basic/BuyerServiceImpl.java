package io.gig.realestate.domain.buyer.basic;

import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.buyer.basic.dto.BuyerCreateForm;
import io.gig.realestate.domain.buyer.basic.dto.BuyerListDto;
import io.gig.realestate.domain.buyer.basic.dto.BuyerSearchDto;
import io.gig.realestate.domain.buyer.detail.BuyerDetail;
import io.gig.realestate.domain.buyer.detail.BuyerDetailService;
import io.gig.realestate.domain.buyer.basic.dto.BuyerDetailDto;
import io.gig.realestate.domain.buyer.detail.dto.BuyerDetailUpdateForm;
import io.gig.realestate.domain.buyer.detail.dto.ProcessDetailDto;
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
    private final BuyerDetailService buyerDetailService;

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
        Category investCharacterCd = null;
        if (createForm.getInvestmentCharacterCd() != null) {
            investCharacterCd = categoryService.getCategoryById(createForm.getInvestmentCharacterCd());
        }
        Buyer buyer = Buyer.create(createForm, processCd, loginUser.getLoginUser());
        BuyerDetail detail = BuyerDetail.create(createForm, processCd, investCharacterCd, buyer, loginUser.getLoginUser());
        buyer.addDetail(detail);
        return buyerStore.store(buyer).getId();
    }

    @Override
    @Transactional
    public Long update(BuyerDetailUpdateForm updateForm, LoginUser loginUser) {
        Category processCd = categoryService.getCategoryById(updateForm.getProcessCd());
        Category investCharacterCd = null;
        if (updateForm.getInvestmentCharacterCd() != null) {
            investCharacterCd = categoryService.getCategoryById(updateForm.getInvestmentCharacterCd());
        }
        Buyer buyer = buyerReader.getBuyerById(updateForm.getBuyerId());
        buyer.update(loginUser);

        Optional<BuyerDetail> findDetail = buyerDetailService.getBuyerDetailByIdAndProcessCd(updateForm.getBuyerId(), updateForm.getProcessCd());
        BuyerDetail detail;
        if (findDetail.isEmpty()) {
            detail = BuyerDetail.create(updateForm, processCd, investCharacterCd, buyer, loginUser.getLoginUser());
            buyer.addDetail(detail);
        } else {
            detail = findDetail.get();
            detail.update(updateForm, processCd, investCharacterCd, loginUser);
        }
        return buyerStore.store(buyer).getId();
    }
}
