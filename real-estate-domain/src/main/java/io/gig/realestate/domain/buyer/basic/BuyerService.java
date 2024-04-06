package io.gig.realestate.domain.buyer.basic;

import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.buyer.basic.dto.*;
import io.gig.realestate.domain.buyer.history.dto.HistoryForm;
import io.gig.realestate.domain.buyer.history.dto.HistoryListDto;
import io.gig.realestate.domain.buyer.maps.dto.HistoryMapForm;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2024/02/24
 */
public interface BuyerService {
    Page<BuyerListDto> getBuyerPageListBySearch(BuyerSearchDto condition);

    Long create(BuyerForm createForm, LoginUser loginUser);

    BuyerDetailDto getBuyerDetail(Long buyerId);

    Long update(BuyerForm updateForm, LoginUser loginUser);

    BuyerModalDto getBuyerDetailModal(Long buyerId, LoginUser loginUser);

    List<HistoryListDto> createHistory(Long buyerId, HistoryForm createForm, LoginUser loginUser);

    Long createHistoryMap(Long buyerId, HistoryMapForm createForm, LoginUser loginUser);
}
