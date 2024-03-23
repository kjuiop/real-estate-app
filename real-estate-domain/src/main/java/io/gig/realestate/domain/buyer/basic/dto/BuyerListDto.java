package io.gig.realestate.domain.buyer.basic.dto;

import io.gig.realestate.domain.buyer.basic.Buyer;
import io.gig.realestate.domain.buyer.detail.BuyerDetail;

/**
 * @author : JAKE
 * @date : 2024/02/24
 */
public class BuyerListDto extends BuyerDto {

    public String processCds = "";
    public String processName;

    public BuyerListDto(Buyer b) {
        super(b);

//        if (b.getBuyerDetails().size() > 0) {
//            StringBuilder processCdStr = new StringBuilder();
//            for (BuyerDetail d : b.getBuyerDetails()) {
//                processCdStr.append(d.getProcessCd().getCode());
//                processCdStr.append(",");
//            }
//            processCds = processCdStr.toString();
//        }
    }
}
