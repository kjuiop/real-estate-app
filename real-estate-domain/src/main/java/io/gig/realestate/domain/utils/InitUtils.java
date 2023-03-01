package io.gig.realestate.domain.utils;

import io.gig.realestate.domain.admin.AdministratorService;
import io.gig.realestate.domain.exception.AlreadyEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author : JAKE
 * @date : 2023/03/01
 */
@Component
@RequiredArgsConstructor
public class InitUtils {

    private final AdministratorService administratorService;

    public void initData() {
        validateAlreadyEntity();
    }


    private void validateAlreadyEntity() {
        if (administratorService.getCountAdministratorData() > 0) {
            throw new AlreadyEntity("이미 관리자 데이터가 존재합니다.");
        }
    }
}
