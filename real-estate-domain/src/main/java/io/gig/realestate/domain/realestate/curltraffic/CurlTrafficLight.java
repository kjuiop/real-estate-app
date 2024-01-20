package io.gig.realestate.domain.realestate.curltraffic;

import io.gig.realestate.domain.common.BaseTimeEntity;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.basic.RealEstate;
import io.gig.realestate.domain.realestate.curltraffic.types.TrafficType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

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

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 50, columnDefinition = "varchar(50) default 'NotYet'")
    private TrafficType landUsageDataApi = TrafficType.NotYet;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 50, columnDefinition = "varchar(50) default 'NotYet'")
    private TrafficType landPriceDataApi = TrafficType.NotYet;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 50, columnDefinition = "varchar(50) default 'NotYet'")
    private TrafficType constructDataApi = TrafficType.NotYet;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 50, columnDefinition = "varchar(50) default 'NotYet'")
    private TrafficType constructFloorDataApi = TrafficType.NotYet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "real_estate_id")
    private RealEstate realEstate;
}
