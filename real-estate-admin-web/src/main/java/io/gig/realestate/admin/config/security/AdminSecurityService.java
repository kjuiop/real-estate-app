package io.gig.realestate.admin.config.security;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.admin.AdministratorService;
import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.admin.dto.AdministratorDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author : JAKE
 * @date : 2023/03/04
 */
@Service
@RequiredArgsConstructor
public class AdminSecurityService implements UserDetailsService {

    private final AdministratorService administratorService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Administrator administrator = administratorService.getAdminEntityByUsername(username);

        // Role
        Set<GrantedAuthority> authorities = administrator.getRoles()
                .stream()
                .map(r -> new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toSet());

        boolean loginEnabled = true;
        boolean accountNonExpired = true;
        boolean credentialNonExpired = true;
        boolean accountNonLocked = true;

        if (administrator.getPasswordFailureCount() >= 5) {
            accountNonLocked = false;
        }

        if (!administrator.isNormal()) {
            loginEnabled = false;
        }

        /**
         *
         * if (!administrator.isValidEmailAuth()) {
         *  credentialNonExpired = false;
         * }
         */

        return new LoginUser(administrator.getUsername(), administrator.getPassword(), loginEnabled, accountNonExpired, credentialNonExpired, accountNonLocked, authorities, administrator);
    }

    public Administrator getLoginUser() {
        if (SecurityContextHolder.getContext() == null) {
            return null;
        }

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            return null;
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof LoginUser) {
            return (Administrator) ((LoginUser) principal).getLoginUser();
        }
        else {
            return null;
        }
    }
}
