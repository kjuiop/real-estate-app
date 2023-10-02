package io.gig.realestate.domain.realestate.memo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author : JAKE
 * @date : 2023/10/02
 */
@SuperBuilder
@Getter
@NoArgsConstructor
public class MemoDto {

    private Long memoId;

    private String memo;
}
