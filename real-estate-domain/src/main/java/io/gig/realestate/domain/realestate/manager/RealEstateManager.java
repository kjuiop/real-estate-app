package io.gig.realestate.domain.realestate.manager;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.common.BaseTimeEntity;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.basic.RealEstate;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

/**
 * @author : JAKE
 * @date : 2024/06/30
 */
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class RealEstateManager extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String name;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 2, columnDefinition = "char(1) default 'N'")
    private YnType deleteYn = YnType.N;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "administrator_id")
    private Administrator admin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "realestate_id")
    private RealEstate realEstate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id")
    private Administrator createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by_id")
    private Administrator updatedBy;

    public static RealEstateManager create(RealEstate realEstate, Administrator manager, Administrator loginAdmin) {
        return RealEstateManager.builder()
                .realEstate(realEstate)
                .name(manager.getName())
                .username(manager.getUsername())
                .admin(manager)
                .createdBy(loginAdmin)
                .updatedBy(loginAdmin)
                .build();
    }

    public void update(RealEstate realEstate, Administrator manager, Administrator loginAdmin) {
        this.updatedBy = loginAdmin;
        this.name = manager.getName();
        this.username = manager.getUsername();
        this.realEstate = realEstate;
    }

    public void delete() {
        this.deleteYn = YnType.Y;
    }
}
