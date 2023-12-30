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

    private String jsonStr;

    private Long realEstateId;

    public VertexDto(Vertex v) {
        this.jsonStr = v.getJsonStr();
        this.realEstateId = v.getRealEstate().getId();
    }
}
