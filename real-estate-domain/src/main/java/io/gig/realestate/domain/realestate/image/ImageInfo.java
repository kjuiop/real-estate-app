package io.gig.realestate.domain.realestate.image;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.image.dto.ImageCreateForm;
import io.gig.realestate.domain.realestate.image.dto.ImageDto;
import io.gig.realestate.domain.common.BaseTimeEntity;
import io.gig.realestate.domain.realestate.basic.RealEstate;
import lombok.*;
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

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 2, columnDefinition = "char(1) default 'N'")
    private YnType deleteYn = YnType.N;

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
                .id(dto.getImageId())
                .fullPath(dto.getFullPath())
                .realEstate(realEstate)
                .createdBy(createdBy)
                .updatedBy(createdBy)
                .build();
    }

    public void update(ImageCreateForm dto, Administrator loginUser) {
        this.fullPath = dto.getFullPath();
        this.updatedBy = loginUser;
    }

    public void delete() {
        this.deleteYn = YnType.Y;
    }
}
