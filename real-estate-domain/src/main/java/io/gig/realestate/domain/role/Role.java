package io.gig.realestate.domain.role;

import io.gig.realestate.domain.common.BaseTimeEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @author : JAKE
 * @date : 2023/03/01
 */
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Role extends BaseTimeEntity implements Serializable {

    @Transient
    private static final long serialVersionUID = 1L;

    private final static String rolePrefix = "ROLE_";

    @Id
    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @Builder.Default
    private int sortOrder = 0;

    public static Role createRole(String name, String description, int sortOrder) {
        validationRoleName(name);

        return Role.builder()
                .name(name)
                .description(description)
                .sortOrder(sortOrder)
                .build();
    }

    private static boolean checkedPrefixRoleName(String name) {
        return name.toUpperCase().startsWith(rolePrefix);
    }

    private static void validationRoleName(String name) {
        if (!StringUtils.hasText(name)) { throw new IllegalArgumentException(""); }
        if (!checkedPrefixRoleName(name)) { throw new RuntimeException(); }
    }
}