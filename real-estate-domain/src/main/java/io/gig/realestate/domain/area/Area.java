package io.gig.realestate.domain.area;

import io.gig.realestate.domain.common.BaseTimeEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/10/03
 */
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Area extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "area_id")
    private Long id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    private int sortOrder;

    private String legalAddressCode;

    private String sido;

    private String gungu;

    private String dong;

    private String ri;

    private String createdAtByLegal;

    private String canceledAtByLegal;

    @Builder.Default
    @Setter(AccessLevel.PRIVATE)
    private int level = 1;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PARENT_ID")
    private Area parent;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sortOrder")
    @Builder.Default
    @JoinColumn(name = "PARENT_ID")
    private List<Area> children = new ArrayList<>();

    public static Area create(String code, String name, int sortOrder, String legalAddressCode, String sido, String gungu, String dong, String ri, String createdAtByLegal, String canceledAtByLegal, int level) {
        return Area.builder()
                .code(code)
                .name(name)
                .sortOrder(sortOrder)
                .legalAddressCode(legalAddressCode)
                .sido(sido)
                .gungu(gungu)
                .dong(dong)
                .ri(ri)
                .createdAtByLegal(createdAtByLegal)
                .canceledAtByLegal(canceledAtByLegal)
                .level(level)
                .build();
    }

    public void addParent(Area parent) {
        this.parent = parent;
        parent.getChildren().add(this);
    }

    public boolean existParent() {
        return this.parent != null;
    }

}
