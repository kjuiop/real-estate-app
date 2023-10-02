package io.gig.realestate.domain.realestate.memo.dto;

import io.gig.realestate.domain.realestate.memo.MemoInfo;

import java.time.format.DateTimeFormatter;

/**
 * @author : JAKE
 * @date : 2023/10/02
 */
public class MemoListDto extends MemoDto {

    public String createdAtFormat;

    public MemoListDto(MemoInfo m) {
        super(m);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm");
        this.createdAtFormat = m.getCreatedAt().format(formatter);
    }
}
