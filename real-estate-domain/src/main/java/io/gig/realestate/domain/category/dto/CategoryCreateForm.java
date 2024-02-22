package io.gig.realestate.domain.category.dto;

import io.gig.realestate.domain.common.YnType;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

/**
 * @author : JAKE
 * @date : 2023/03/29
 */
@Getter
public class CategoryCreateForm {

    @NotEmpty(message = "카테고리 이름을 입력해주세요.")
    private String name;

    private String code;

    private String colorCode;

    private YnType activeYn;

    private int sortOrder;

    private Long parentId;

    public boolean existParentId() {
        return this.parentId != null;
    }
}
