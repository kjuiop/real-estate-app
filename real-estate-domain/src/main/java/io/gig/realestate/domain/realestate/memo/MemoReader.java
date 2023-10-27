package io.gig.realestate.domain.realestate.memo;

import io.gig.realestate.domain.realestate.memo.dto.MemoListDto;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/10/02
 */
public interface MemoReader {
    List<MemoListDto> getMemoListInfoByRealEstateId(Long realEstateId);

    MemoInfo getMemoInfoById(Long memoId);

    List<MemoInfo> getMemoListByIds(List<Long> memoIds);
}
