package io.gig.realestate.domain.buyer.dto;

import io.gig.realestate.domain.buyer.Buyer;
import io.gig.realestate.domain.buyer.BuyerDetail;

/**
 * @author : JAKE
 * @date : 2024/02/24
 */
public class BuyerListDto extends BuyerDto {

    public String processCds = "";
    public String processName;

    public BuyerListDto(Buyer b) {
        super(b);

        if (b.getProcessCd() != null) {
            this.processName = b.getProcessCd().getName();
        }

        if (b.getBuyerDetails().size() > 0) {
            StringBuilder processCdStr = new StringBuilder();
            for (BuyerDetail d : b.getBuyerDetails()) {
                processCdStr.append(d.getProcessCd().getCode());
                processCdStr.append(",");
            }
            processCds = processCdStr.toString();
        }
    }
}
