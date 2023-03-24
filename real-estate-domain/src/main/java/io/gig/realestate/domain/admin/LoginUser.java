package io.gig.realestate.domain.admin;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * @author : JAKE
 * @date : 2023/03/04
 */
@Getter
public class LoginUser extends User implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Administrator loginUser;
    @Setter
    private Map<String, Object> attributes;
    @Setter
    private boolean needsModified = false;

    public LoginUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, Administrator loginUser) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.loginUser = loginUser;
    }

    public String getName() {
        return this.loginUser.getUsername();
    }
}
