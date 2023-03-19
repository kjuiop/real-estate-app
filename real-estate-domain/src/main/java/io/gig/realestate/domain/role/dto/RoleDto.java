package io.gig.realestate.domain.role.dto;

import io.gig.realestate.domain.role.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : JAKE
 * @date : 2023/03/19
 */
@Getter
@NoArgsConstructor
public class RoleDto {

    private String name;

    private String description;

    private int sortOrder;

    public RoleDto(Role r) {
        this.name = r.getName();
        this.description = r.getDescription();
        this.sortOrder = r.getSortOrder();
    }

}
