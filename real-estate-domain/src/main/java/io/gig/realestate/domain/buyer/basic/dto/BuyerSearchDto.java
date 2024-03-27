package io.gig.realestate.domain.buyer.basic.dto;

import io.gig.realestate.domain.common.BaseSearchDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * @author : JAKE
 * @date : 2024/02/17
 */
@Getter
@Setter
@NoArgsConstructor
public class BuyerSearchDto extends BaseSearchDto {

    private String title;

    private String buyerGradeCds;

    private String purposeCds;

    private String customerName;

    private int minSalePrice;

    private int maxSalePrice;

    private Long teamId;

    private String managerName;

    public PageRequest getPageableWithSort() {
        return PageRequest.of(getPage(), getSize(), Sort.by(new Sort.Order(Sort.Direction.DESC, "id")));
    }
}
