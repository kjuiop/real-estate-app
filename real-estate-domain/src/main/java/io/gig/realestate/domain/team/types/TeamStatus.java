package io.gig.realestate.domain.team.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : JAKE
 * @date : 2023/03/01
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum TeamStatus {

    ACTIVE("Active", "활성"),

    INACTIVE("InActive", "비활성");

    private String key;

    private String description;
}
