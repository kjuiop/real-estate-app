package io.gig.realestate.domain.role;

import io.gig.realestate.domain.common.BaseTimeEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

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

    @Id
    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @Builder.Default
    private int sortOrder = 0;

}