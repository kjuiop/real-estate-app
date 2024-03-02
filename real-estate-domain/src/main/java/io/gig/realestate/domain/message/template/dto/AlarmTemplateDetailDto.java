package io.gig.realestate.domain.message.template.dto;

import io.gig.realestate.domain.message.template.AlarmTemplate;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * @author : JAKE
 * @date : 2024/03/02
 */
@SuperBuilder
@Getter
public class AlarmTemplateDetailDto extends AlarmTemplateDto {

    private static final AlarmTemplateDetailDto EMPTY;

    static {
        EMPTY = AlarmTemplateDetailDto.builder()
                .build();
    }

    public static AlarmTemplateDetailDto emptyDto() {
        return EMPTY;
    }

    public AlarmTemplateDetailDto(AlarmTemplate at) {
        super(at);
    }
}
