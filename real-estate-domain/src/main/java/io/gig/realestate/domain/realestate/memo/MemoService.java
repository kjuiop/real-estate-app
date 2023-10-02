package io.gig.realestate.domain.realestate.memo;

import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.realestate.memo.dto.MemoCreateForm;
import io.gig.realestate.domain.realestate.memo.dto.MemoListDto;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/10/02
 */
public interface MemoService {
    Long create(MemoCreateForm createForm, LoginUser loginUser);

    List<MemoListDto> getMemoListInfoByRealEstateId(Long realEstateId);
}
