package io.gig.realestate.domain.admin;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/03/04
 */
public interface AdministratorStore {

    Administrator store(Administrator administrator);
    List<Administrator> storeAll(List<Administrator> administratorList);
}
