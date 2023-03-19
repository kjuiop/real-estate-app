package io.gig.realestate.domain.menu.types;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author : JAKE
 * @date : 2023/03/19
 */
@Getter
@RequiredArgsConstructor
public enum MenuType {

    AdminConsole("admin", "관리자"),
    Web("front", "프론트");

    final private String type;
    final private String description;
}
