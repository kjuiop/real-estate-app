package io.gig.realestate.domain.realestate.basic;

import io.gig.realestate.domain.common.BaseSearchDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * @author : JAKE
 * @date : 2023/04/14
 */
@Getter
@Setter
@NoArgsConstructor
public class RealEstateSearchDto extends BaseSearchDto {

    private String name;

    public PageRequest getPageableWithSort() {
        return PageRequest.of(getPage(), getSize(), Sort.by(new Sort.Order(Sort.Direction.DESC, "id")));
    }

}
