package io.gig.realestate.domain.realestate.land;

import io.gig.realestate.domain.realestate.basic.RealEstate;
import io.gig.realestate.domain.team.Team;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

/**
 * @author : JAKE
 * @date : 2023/09/23
 */
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class LandInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pnu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "real_estate_id")
    private RealEstate realEstate;
}
