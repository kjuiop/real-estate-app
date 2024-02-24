package io.gig.realestate.domain.buyer;

import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.buyer.dto.BuyerCreateForm;
import io.gig.realestate.domain.buyer.dto.BuyerListDto;
import io.gig.realestate.domain.buyer.dto.BuyerSearchDto;
import io.gig.realestate.domain.category.Category;
import io.gig.realestate.domain.category.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public Long create(BuyerCreateForm createForm, LoginUser loginUser) {
        Category processCd = categoryService.getCategoryById(createForm.getProcessCd());
        Buyer buyer = Buyer.create(createForm, processCd, loginUser.getLoginUser());
        BuyerDetail detail = BuyerDetail.create(createForm, processCd, buyer, loginUser.getLoginUser());
        buyer.addDetail(detail);
        return buyerStore.store(buyer).getId();
    }
}
