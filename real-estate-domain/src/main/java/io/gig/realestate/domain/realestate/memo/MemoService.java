package io.gig.realestate.domain.realestate.memo;

import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.realestate.memo.dto.MemoCreateForm;

/**
 * @author : JAKE
 * @date : 2023/10/02
 */
public interface MemoService {
    Long create(MemoCreateForm createForm, LoginUser loginUser);
}
