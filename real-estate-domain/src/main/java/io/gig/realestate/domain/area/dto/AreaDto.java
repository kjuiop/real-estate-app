package io.gig.realestate.domain.area.dto;

import io.gig.realestate.domain.area.Area;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author : JAKE
 * @date : 2023/10/03
 */
@SuperBuilder
@Getter
@NoArgsConstructor
public class AreaDto {

    private Long areaId;

    private Long parentId;

    private String code;

    private String name;

    private int sortOrder;

    private String legalAddressCode;

    private String sido;

    private String gungu;

    private String dong;

    private String ri;

    private String createdAtByLegal;

    private String canceledAtByLegal;

    private int level;

    public AreaDto(Area a) {
        this.areaId = a.getId();
        this.code = a.getCode();
        this.name = a.getName();
        this.sortOrder = a.getSortOrder();
        this.legalAddressCode = a.getLegalAddressCode();
        this.sido = a.getSido();
        this.gungu = a.getGungu();
        this.dong = a.getDong();
        this.ri = a.getRi();
        this.createdAtByLegal = a.getCreatedAtByLegal();
        this.canceledAtByLegal = a.getCanceledAtByLegal();
        this.level = a.getLevel();
        if (a.getParent() != null) {
            this.parentId = a.getParent().getId();
        }
    }
}
