package io.gig.realestate.domain.realestate.memo.dto;

import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.memo.MemoInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

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

    private String createdByName;

    private YnType deleteYn;

    private LocalDateTime createdAt;

    public MemoDto(MemoInfo m) {
        this.memoId = m.getId();
        this.memo = m.getMemo();
        this.deleteYn = m.getDeleteYn();
        if (m.getCreatedBy() != null) {
            this.createdByName = m.getCreatedBy().getName();
        }
        this.createdAt = m.getCreatedAt();
    }
}
