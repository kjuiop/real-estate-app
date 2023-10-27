package io.gig.realestate.domain.realestate.memo;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/10/09
 */
public interface MemoStore {
    void saveAll(List<MemoInfo> memoList);
}
