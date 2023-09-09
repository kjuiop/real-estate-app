package io.gig.realestate.domain.category;

import io.gig.realestate.domain.category.dto.CategoryCreateForm;
import io.gig.realestate.domain.category.dto.CategoryUpdateForm;
import io.gig.realestate.domain.common.YnType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/03/29
 */
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Builder.Default
    @Setter(AccessLevel.PRIVATE)
    private int level = 1;

    @Builder.Default
    private int sortOrder = 0;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 2)
    private YnType activeYn = YnType.N;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @Builder.Default
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Category> child = new ArrayList<>();

    public static Category create(CategoryCreateForm form) {
        return Category.builder()
                .name(form.getName())
                .activeYn(form.getActiveYn())
                .sortOrder(form.getSortOrder())
                .build();
    }

    public void update(CategoryUpdateForm form) {
        this.name = form.getName();
        this.sortOrder = form.getSortOrder();
        this.activeYn = form.getActiveYn();
    }

    public void addParent(Category parent) {
        this.parent = parent;
        this.level = 2;
        parent.getChild().add(this);
    }


}
