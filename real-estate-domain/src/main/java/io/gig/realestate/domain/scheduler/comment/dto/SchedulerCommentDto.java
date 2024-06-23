package io.gig.realestate.domain.scheduler.comment.dto;

import io.gig.realestate.domain.scheduler.comment.SchedulerComment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * @author : JAKE
 * @date : 2024/06/23
 */
@SuperBuilder
@Getter
@NoArgsConstructor
public class SchedulerCommentDto {

    private String comment;

    private String createdName;

    private LocalDateTime createdAt;

    public SchedulerCommentDto(SchedulerComment s) {
        this.comment = s.getComment();
        this.createdName = s.getCreatedBy().getName();
        this.createdAt = s.getCreatedAt();
    }
}
