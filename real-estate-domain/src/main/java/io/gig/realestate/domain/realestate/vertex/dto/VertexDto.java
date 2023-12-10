package io.gig.realestate.domain.realestate.vertex.dto;

import io.gig.realestate.domain.realestate.vertex.Vertex;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : JAKE
 * @date : 2023/12/10
 */
@Getter
@Setter
public class VertexDto {

    private double x;

    private double y;

    private Long realEstateId;

    public VertexDto(Vertex v) {
        this.x = v.getX();
        this.y = v.getY();
        this.realEstateId = v.getRealEstate().getId();
    }
}
