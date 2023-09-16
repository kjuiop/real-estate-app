package io.gig.realestate.domain.category.dto;

import io.gig.realestate.domain.common.YnType;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author : JAKE
 * @date : 2023/03/29
 */
@Getter
public class CategoryUpdateForm {

    @NotNull
    private Long id;

    @NotEmpty(message = "카테고리 이름을 입력해주세요.")
    private String name;

    private YnType activeYn;

    private int sortOrder;
}
