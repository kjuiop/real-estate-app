package io.gig.realestate.domain.realestate.memo.repository;

import io.gig.realestate.domain.realestate.memo.MemoInfo;
import io.gig.realestate.domain.realestate.memo.MemoReader;
import io.gig.realestate.domain.realestate.memo.dto.MemoListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/10/02
 */
@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemoQueryImpl implements MemoReader {

    private final MemoQueryRepository queryRepository;

    @Override
    public List<MemoListDto> getMemoListInfoByRealEstateId(Long realEstateId, boolean allMemo) {
        return queryRepository.getMemoListInfoByRealEstateId(realEstateId, allMemo);
    }

    @Override
    public MemoInfo getMemoInfoById(Long memoId) {
        return queryRepository.getMemoInfoById(memoId);
    }

    @Override
    public List<MemoInfo> getMemoListByIds(List<Long> memoIds) {
        return queryRepository.getMemoListByIds(memoIds);
    }
}
