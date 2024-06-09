package io.gig.realestate.domain.buyer.basic.dto;

import io.gig.realestate.domain.buyer.basic.Buyer;
import io.gig.realestate.domain.buyer.basic.types.CompanyScaleType;
import io.gig.realestate.domain.buyer.history.dto.HistoryListDto;
import io.gig.realestate.domain.buyer.manager.BuyerManager;
import io.gig.realestate.domain.buyer.manager.dto.BuyerManagerDto;
import io.gig.realestate.domain.buyer.maps.dto.HistoryMapListDto;
import io.gig.realestate.domain.common.YnType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : JAKE
 * @date : 2024/02/18
 */
@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BuyerDetailDto extends BuyerDto {

    private static final BuyerDetailDto EMPTY;

    public String purposeNameStr;
    public String preferBuildingNameStr;
    public String investmentTimingNameStr;
    public String loanCharacterNameStr;
    public String buyerGradeName;
    public Integer salePriceRange;
    public Long teamId;
    public Long createdByAdminId;

    @Builder.Default
    public List<HistoryListDto> histories = new ArrayList<>();

    @Builder.Default
    public List<HistoryMapListDto> maps = new ArrayList<>();

    @Builder.Default
    public List<BuyerManagerDto> managers = new ArrayList<>();

    @Builder.Default
    private boolean empty = false;

    static {
        EMPTY = BuyerDetailDto.builder()
                .empty(true)
                .companyScale(CompanyScaleType.Large)
                .companyEstablishAtYn(YnType.N)
                .build();
    }

    public static BuyerDetailDto emptyDto() {
        return EMPTY;
    }

    public void setPurposeNameStr(String purposeNameStr) {
        this.purposeNameStr = purposeNameStr;
    }

    public void setPreferBuildingName(String preferBuildingNames) {
        this.preferBuildingNameStr = preferBuildingNames;
    }

    public void setInvestmentTimingNames(String investmentTimingNames) {
        this.investmentTimingNameStr = investmentTimingNames;
    }

    public void setLoanCharacterNames(String loanCharacterNames) {
        this.loanCharacterNameStr = loanCharacterNames;
    }

    public BuyerDetailDto(Buyer b) {
        super(b);
        List<BuyerManagerDto> list = new ArrayList<>();
        for (BuyerManager bm : b.getManagers()) {
            if (bm.getDeleteYn() == YnType.N && bm.getAdmin().isNormal()) {
                list.add(new BuyerManagerDto(bm));
            }
        }
        this.managers = list;
        this.createdByAdminId = b.getCreatedBy().getId();
    }

    public void setHistories(List<HistoryListDto> histories) {
        this.histories = histories;
    }

    public void setHistoryMap(List<HistoryMapListDto> maps) {
        this.maps = maps;
    }

    public void setBuyerGradeName(String buyerGradeName) {
        this.buyerGradeName = buyerGradeName;
    }

    public void convertSalePriceIntValue(double salePrice) {
        this.salePriceRange = (int) salePrice;
    }
}
