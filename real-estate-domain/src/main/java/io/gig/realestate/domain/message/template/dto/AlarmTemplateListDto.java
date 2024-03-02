package io.gig.realestate.domain.message.template.dto;

import io.gig.realestate.domain.message.template.AlarmTemplate;

/**
 * @author : JAKE
 * @date : 2024/03/02
 */
public class AlarmTemplateListDto extends AlarmTemplateDto {

    public String createdByName;

    public AlarmTemplateListDto(AlarmTemplate at) {
        super(at);

        if (at.getCreatedBy() != null) {
            this.createdByName = at.getCreatedBy().getName();
        }
    }
}
