package io.gig.realestate.domain.admin.dto;

import io.gig.realestate.domain.admin.types.AdminStatus;
import lombok.Getter;

/**
 * @author : Jake
 * @date : 2021-08-26
 */
@Getter
public class AdminStatusUpdateForm {

    private Long adminId;

    private AdminStatus status;
}
