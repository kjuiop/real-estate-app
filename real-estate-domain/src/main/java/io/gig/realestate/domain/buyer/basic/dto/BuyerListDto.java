package io.gig.realestate.domain.buyer.basic.dto;

import io.gig.realestate.domain.buyer.basic.Buyer;
import io.gig.realestate.domain.buyer.detail.BuyerDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2024/02/24
 */
public class BuyerListDto extends BuyerDto {

    public String buyerGradeName;
    public List<String> purposeNames = new ArrayList<>();

    public String purposeNameStr;
    public String preferBuildingNameStr;
    public String investmentTimingNameStr;
    public String loanCharacterNameStr;


    public Integer salePriceRange;


    public String processCds = "";
    public String processName;

    public BuyerListDto(Buyer b) {
        super(b);
    }

    public void setBuyerGradeName(String buyerGradeName) {
        this.buyerGradeName = buyerGradeName;
    }

    public void setPurposeName(List<String> purposeNames) {
        this.purposeNames = purposeNames;
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

    public void convertSalePriceIntValue(double salePrice) {
        this.salePriceRange = (int) salePrice;
    }
}
