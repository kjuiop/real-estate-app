package io.gig.realestate.domain.message.template.dto;

import io.gig.realestate.domain.common.BaseSearchDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * @author : JAKE
 * @date : 2024/02/17
 */
@Getter
@Setter
@NoArgsConstructor
public class AlarmTemplateSearchDto extends BaseSearchDto {

    private String title;

    public PageRequest getPageableWithSort() {
        return PageRequest.of(getPage(), getSize(), Sort.by(new Sort.Order(Sort.Direction.DESC, "id")));
    }
}
