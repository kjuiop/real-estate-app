package io.gig.realestate.domain.menu.dto;

import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.menu.types.MenuType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/03/20
 */
@Getter
@Setter
public class MenuCreateForm {

    @NotEmpty(message = "메뉴이름을 입력해주세요.")
    private String name;

    @NotEmpty(message = "메뉴 URL을 입력해주세요.")
    private String url;

    private String iconClass;

    private YnType activeYn;

    private YnType displayYn;

    private int sortOrder;

    private MenuType menuType;

    private Long parentId;

    @NotEmpty(message = "역할을 활성화해주세요.")
    private List<String> roleNames = new ArrayList<>();

    public boolean existParentId() {
        return this.parentId != null;
    }
}