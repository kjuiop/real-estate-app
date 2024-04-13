package io.gig.realestate.domain.buyer.history.dto;

import io.gig.realestate.domain.buyer.history.BuyerHistory;
import io.gig.realestate.domain.buyer.realestate.dto.HistoryRealEstateDto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2024/04/03
 */
public class HistoryListDto extends HistoryDto {

    public List<HistoryRealEstateDto> realEstateList = new ArrayList<>();

    public HistoryListDto(BuyerHistory h) {
        super(h);
    }

    public void setRealEstateList(List<HistoryRealEstateDto> list) {
        this.realEstateList = list;
    }
}
