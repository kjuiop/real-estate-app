package io.gig.realestate.domain.realestate.memo;

import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.realestate.basic.RealEstate;
import io.gig.realestate.domain.realestate.basic.RealEstateReader;
import io.gig.realestate.domain.realestate.basic.RealEstateStore;
import io.gig.realestate.domain.realestate.memo.dto.MemoCreateForm;
import io.gig.realestate.domain.realestate.memo.dto.MemoDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : JAKE
 * @date : 2023/10/02
 */
@Service
@RequiredArgsConstructor
public class MemoServiceImpl implements MemoService {

    private final RealEstateReader realEstateReader;
    private final RealEstateStore realEstateStore;

    @Override
    @Transactional
    public Long create(MemoCreateForm createForm, LoginUser loginUser) {

        RealEstate newRealEstate = realEstateReader.getRealEstateById(createForm.getRealEstateId());
        MemoInfo memo = MemoInfo.create(createForm, newRealEstate, loginUser.getLoginUser());
        newRealEstate.addMemoInfo(memo);
        return realEstateStore.store(newRealEstate).getId();
    }
}
