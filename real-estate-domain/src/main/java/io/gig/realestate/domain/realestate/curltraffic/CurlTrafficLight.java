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

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 50, columnDefinition = "varchar(50) default 'NotYet'")
    private TrafficType landDataApi = TrafficType.NotYet;

    private LocalDateTime lastCurlLandApiAt;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 50, columnDefinition = "varchar(50) default 'NotYet'")
    private TrafficType landUsageDataApi = TrafficType.NotYet;

    private LocalDateTime lastCurlLandUsageApiAt;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 50, columnDefinition = "varchar(50) default 'NotYet'")
    private TrafficType landPriceDataApi = TrafficType.NotYet;

    private LocalDateTime lastCurlLandPriceApiAt;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 50, columnDefinition = "varchar(50) default 'NotYet'")
    private TrafficType constructDataApi = TrafficType.NotYet;

    private LocalDateTime lastConstructApiAt;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 50, columnDefinition = "varchar(50) default 'NotYet'")
    private TrafficType constructFloorDataApi = TrafficType.NotYet;

    private LocalDateTime lastConstructFloorApiAt;

    private String pnu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "real_estate_id")
    private RealEstate realEstate;

    public static CurlTrafficLight createByLand(int statusCode, String pnu) {
        return CurlTrafficLight.builder()
                .pnu(pnu)
                .landDataApi(setTrafficType(statusCode))
                .lastCurlLandApiAt(LocalDateTime.now())
                .build();
    }

    private static TrafficType setTrafficType(int statusCode) {
        if (statusCode >= 200 && statusCode < 300) {
            return TrafficType.Success;
        }
        return TrafficType.Fail;
    }
}
