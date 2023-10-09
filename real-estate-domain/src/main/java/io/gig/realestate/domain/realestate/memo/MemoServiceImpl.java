package io.gig.realestate.domain.realestate.memo;

import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.realestate.basic.RealEstate;
import io.gig.realestate.domain.realestate.basic.RealEstateReader;
import io.gig.realestate.domain.realestate.basic.RealEstateStore;
import io.gig.realestate.domain.realestate.memo.dto.MemoCreateForm;
import io.gig.realestate.domain.realestate.memo.dto.MemoDeleteForm;
import io.gig.realestate.domain.realestate.memo.dto.MemoListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/10/02
 */
@Service
@RequiredArgsConstructor
public class MemoServiceImpl implements MemoService {

    private final RealEstateReader realEstateReader;
    private final RealEstateStore realEstateStore;

    private final MemoReader memoReader;

    @Override
    @Transactional(readOnly = true)
    public List<MemoListDto> getMemoListInfoByRealEstateId(Long realEstateId) {
        return memoReader.getMemoListInfoByRealEstateId(realEstateId);
    }

    @Override
    @Transactional
    public Long create(MemoCreateForm createForm, LoginUser loginUser) {

        RealEstate newRealEstate = realEstateReader.getRealEstateById(createForm.getRealEstateId());
        MemoInfo memo = MemoInfo.create(createForm, newRealEstate, loginUser.getLoginUser());
        newRealEstate.addMemoInfo(memo);
        return realEstateStore.store(newRealEstate).getId();
    }

    @Override
    @Transactional
    public Long delete(MemoDeleteForm deleteForm, LoginUser loginUser) {
        MemoInfo memoInfo = memoReader.getMemoInfoById(deleteForm.getMemoId());
        memoInfo.delete();
        return deleteForm.getRealEstateId();
    }
}
