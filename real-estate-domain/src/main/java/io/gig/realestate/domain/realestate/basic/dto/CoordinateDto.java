package io.gig.realestate.domain.realestate.basic.dto;

import io.gig.realestate.domain.realestate.basic.RealEstate;
import io.gig.realestate.domain.realestate.vertex.dto.VertexDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : JAKE
 * @date : 2023/12/06
 */
@Getter
@Setter
public class CoordinateDto {

    private Long realEstateId;

    private String address;

    private String buildingName;

    private double salePrice;

    private List<VertexDto> vertexInfoList;

    public CoordinateDto(RealEstate r) {
        this.realEstateId = r.getId();
        this.buildingName = r.getBuildingName();
        this.address = r.getAddress();

        if (r.getPriceInfoList() != null && r.getPrintInfoList().size() > 0) {
            this.salePrice = r.getPriceInfoList().get(0).getSalePrice();
        }

//        this.vertexInfoList = r.getVertexInfoList().stream().map(VertexDto::new).collect(Collectors.toList());
    }
}
