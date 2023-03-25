package io.gig.realestate.domain.admin.dto;

import io.gig.realestate.domain.admin.Administrator;

/**
 * @author : JAKE
 * @date : 2023/03/25
 */
public class AdministratorListDto extends AdministratorDto {

    private String createdByUsername;

    public AdministratorListDto(Administrator a) {
        super(a);
        if (a.getCreatedBy() != null) {
            this.createdByUsername = a.getCreatedBy().getUsername();
        }
    }

}
