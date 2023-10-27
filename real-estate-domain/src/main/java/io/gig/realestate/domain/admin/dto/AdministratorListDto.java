package io.gig.realestate.domain.admin.dto;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.admin.AdministratorRole;

/**
 * @author : JAKE
 * @date : 2023/03/25
 */
public class AdministratorListDto extends AdministratorDto {

    public String createdByUsername;
    public Long teamId;
    public String teamName;
    public String roleName;

    public AdministratorListDto(Administrator a) {
        super(a);
        if (a.getCreatedBy() != null) {
            this.createdByUsername = a.getCreatedBy().getUsername();
        }

        if (a.getTeam() != null) {
            this.teamId = a.getTeam().getId();
            this.teamName = a.getTeam().getName();
        }

        if (a.getAdministratorRoles().size() > 0) {
            for (AdministratorRole role : a.getAdministratorRoles()) {
                this.roleName = role.getRole().getDescription();
            }
        }
    }

}
