package io.gig.realestate.domain.buyer.basic.dto;

import io.gig.realestate.domain.buyer.basic.Buyer;
import io.gig.realestate.domain.buyer.manager.BuyerManager;
import io.gig.realestate.domain.buyer.manager.dto.BuyerManagerDto;
import io.gig.realestate.domain.buyer.maps.dto.HistoryMapListDto;
import io.gig.realestate.domain.common.YnType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2024/02/24
 */
public class BuyerListDto extends BuyerDto {

    public String buyerGradeName;
    public List<String> purposeNames = new ArrayList<>();
    public Integer salePriceRange;
    public List<HistoryMapListDto> maps = new ArrayList<>();
    public List<BuyerManagerDto> managers = new ArrayList<>();


    public String processCds = "";
    public String processName;

    public BuyerListDto(Buyer b) {
        super(b);
        if (!b.getManagers().isEmpty()) {
            List<BuyerManagerDto> list = new ArrayList<>();
            for (BuyerManager bm : b.getManagers()) {
                if (bm.getDeleteYn() == YnType.N && bm.getAdmin().isNormal()) {
                    list.add(new BuyerManagerDto(bm));
                }
            }
            this.managers = list;
        }
    }

    public void setBuyerGradeName(String buyerGradeName) {
        this.buyerGradeName = buyerGradeName;
    }

    public void setPurposeName(List<String> purposeNames) {
        this.purposeNames = purposeNames;
    }

    public void convertSalePriceIntValue(double salePrice) {
        this.salePriceRange = (int) salePrice;
    }

    public void setHistoryMap(List<HistoryMapListDto> maps) {
        this.maps = maps;
    }
}
