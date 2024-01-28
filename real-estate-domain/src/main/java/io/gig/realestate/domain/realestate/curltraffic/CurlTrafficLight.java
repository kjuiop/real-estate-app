package io.gig.realestate.domain.realestate.curltraffic;

import io.gig.realestate.domain.common.BaseTimeEntity;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.basic.RealEstate;
import io.gig.realestate.domain.realestate.curltraffic.types.TrafficType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author : JAKE
 * @date : 2024/01/19
 */
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CurlTrafficLight extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int landDataResCode;

    private LocalDateTime lastCurlLandApiAt;

    private int landUsageDataResCode;

    private LocalDateTime lastCurlLandUsageApiAt;

    private int landPriceDataResCode;

    private LocalDateTime lastCurlLandPriceApiAt;

    private int constructDataResCode;

    private LocalDateTime lastConstructApiAt;

    private int constructFloorResCode;

    private LocalDateTime lastConstructFloorApiAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "real_estate_id")
    private RealEstate realEstate;

    public static CurlTrafficLight initTrafficLight(RealEstate realEstate) {
        return CurlTrafficLight.builder()
                .realEstate(realEstate)
                .build();
    }

    public void setLandDataApiResult(int resCode, LocalDateTime lastCurlLandApiAt) {
        this.landDataResCode = resCode;
        this.lastCurlLandApiAt = lastCurlLandApiAt;
    }

    public void setLandUsageDataApiResult(int resCode, LocalDateTime lastCurlLandUsageApiAt) {
        this.landUsageDataResCode = resCode;
        this.lastCurlLandUsageApiAt = lastCurlLandUsageApiAt;
    }

    public void setFloorDataApiResult(int resCode, LocalDateTime lastCurlFloorApiAt) {
        this.constructFloorResCode = resCode;
        this.lastConstructFloorApiAt = lastCurlFloorApiAt;
    }

    public void setConstructDataApiResult(int resCode, LocalDateTime lastCurlApiAt) {
        this.constructDataResCode = resCode;
        this.lastConstructApiAt = lastCurlApiAt;
    }

    public void setLandPriceDataApiResult(int landPriceResCode, LocalDateTime landPriceLastCurlApiAt) {
        this.landPriceDataResCode = landPriceResCode;
        this.lastCurlLandPriceApiAt = landPriceLastCurlApiAt;
    }
}
