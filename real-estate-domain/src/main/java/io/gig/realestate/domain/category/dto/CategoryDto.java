package io.gig.realestate.domain.category.dto;

import io.gig.realestate.domain.category.Category;
import io.gig.realestate.domain.common.YnType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : JAKE
 * @date : 2023/03/29
 */
@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {

    private Long id;

    private Long parentId;

    private String code;

    private String name;

    private String colorCode;

    private YnType activeYn;

    private int sortOrder;

    private int level;

    private List<CategoryDto> child = new ArrayList<>();

    public CategoryDto(Category c) {
        this.id = c.getId();
        this.code = c.getCode();
        this.name = c.getName();
        this.colorCode = c.getColorCode();
        this.activeYn = c.getActiveYn();
        this.sortOrder = c.getSortOrder();
        this.level = c.getLevel();

        if (c.getParent() != null) {
            this.parentId = c.getParent().getId();
        }
    }

    public CategoryDto(Category c, boolean needsChild) {
        this(c);
        if (needsChild) {
            if (!CollectionUtils.isEmpty(c.getChild())) {
                this.child = c.getChild().stream().map(ch -> new CategoryDto(ch, true)).collect(Collectors.toList());
            }
        }
    }

    public void addChildren(List<CategoryDto> children) {
        this.child = children;
    }

}


