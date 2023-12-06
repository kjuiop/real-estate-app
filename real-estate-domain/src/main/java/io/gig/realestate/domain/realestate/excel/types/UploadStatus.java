package io.gig.realestate.domain.realestate.excel.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : JAKE
 * @date : 2023/03/01
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum UploadStatus {

    PENDING("pending", "진행중"),

    COMPLETE("complete", "완료"),

    FAIL("fail", "실패");

    private String key;

    private String description;
}
