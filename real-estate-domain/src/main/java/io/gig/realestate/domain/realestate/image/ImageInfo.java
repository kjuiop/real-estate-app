package io.gig.realestate.domain.realestate.image;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.realestate.image.dto.ImageCreateForm;
import io.gig.realestate.domain.realestate.image.dto.ImageDto;
import io.gig.realestate.domain.common.BaseTimeEntity;
import io.gig.realestate.domain.realestate.basic.RealEstate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

/**
 * @author : JAKE
 * @date : 2023/10/21
 */
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageInfo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "real_estate_id")
    private RealEstate realEstate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id")
    private Administrator createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by_id")
    private Administrator updatedBy;

    public static ImageInfo create(ImageCreateForm dto, RealEstate realEstate, Administrator createdBy) {
        return ImageInfo.builder()
                .fullPath(dto.getFullPath())
                .realEstate(realEstate)
                .createdBy(createdBy)
                .updatedBy(createdBy)
                .build();
    }
}
