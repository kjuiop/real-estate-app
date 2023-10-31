package io.gig.realestate.domain.admin.dto;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.admin.types.AdminStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : JAKE
 * @date : 2023/03/18
 */
@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdministratorDetailDto extends AdministratorDto {

    public Long teamId;

    private static final AdministratorDetailDto EMPTY;

    static {
        EMPTY = AdministratorDetailDto.builder()
                .empty(true)
                .status(AdminStatus.PENDING)
                .build();
    }

    @Builder.Default
    private boolean empty = false;

    @Builder.Default
    List<String> roles = new ArrayList<>();

    public static AdministratorDetailDto emptyDto() {
        return EMPTY;
    }

    public AdministratorDetailDto(Administrator a) {
        super(a);
        this.roles = a.getAdministratorRoles().stream().map(role -> role.getRole().getName()).collect(Collectors.toList());

        if (a.getTeam() != null) {
            this.teamId = a.getTeam().getId();
        }
    }

}
