package io.gig.realestate.domain.buyer.basic.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.gig.realestate.domain.admin.types.AdminStatus;
import io.gig.realestate.domain.common.BaseSearchDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author : JAKE
 * @date : 2024/02/17
 */
@Getter
@Setter
@NoArgsConstructor
public class BuyerSearchDto extends BaseSearchDto {

    private String title;

    private String preferArea;

    private String preferSubway;

    private String buyerGradeCds;

    private String purposeCds;

    private String customerName;

    private Integer minLandAreaPy;

    private Integer maxLandAreaPy;

    private Integer minTotalAreaPy;

    private Integer maxTotalAreaPy;

    private Integer minExclusiveAreaPy;

    private Integer maxExclusiveAreaPy;

    private Integer minSalePrice;

    private Integer maxSalePrice;

    private Integer minSuccessPercent;

    private Integer maxSuccessPercent;

    private Long teamId;

    private String managerName;

    private String searchDateUnit;

    private boolean isWithDraw = false;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime beforeCreatedAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime afterCreatedAt;

    public PageRequest getPageableWithSort() {
        return PageRequest.of(getPage(), getSize(), Sort.by(new Sort.Order(Sort.Direction.DESC, "id")));
    }
}
