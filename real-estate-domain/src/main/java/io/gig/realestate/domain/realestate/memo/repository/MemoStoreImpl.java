package io.gig.realestate.domain.realestate.memo.repository;

import io.gig.realestate.domain.realestate.memo.MemoInfo;
import io.gig.realestate.domain.realestate.memo.MemoStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/10/09
 */
@Component
@Transactional
@RequiredArgsConstructor
public class MemoStoreImpl implements MemoStore {

    private final MemoStoreRepository storeRepository;

    @Override
    public void saveAll(List<MemoInfo> memoList) {
        storeRepository.saveAll(memoList);
    }
}
