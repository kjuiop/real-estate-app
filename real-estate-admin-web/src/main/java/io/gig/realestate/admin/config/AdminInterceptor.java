package io.gig.realestate.admin.config;

import io.gig.realestate.admin.config.security.AdminSecurityService;
import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.admin.AdministratorService;
import io.gig.realestate.domain.menu.MenuService;
import io.gig.realestate.domain.menu.dto.MenuDto;
import io.gig.realestate.domain.menu.types.MenuType;
import io.gig.realestate.domain.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/03/19
 */
@Component
@RequiredArgsConstructor
public class AdminInterceptor implements HandlerInterceptor {

    private final AdminSecurityService adminSecurityService;
    private final MenuService menuService;
    private final NotificationService notificationService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mav) throws Exception {

        if (mav == null) {
            return;
        }

        Administrator loginUser = adminSecurityService.getLoginUser();
        if (loginUser != null) {
            List<MenuDto> menus = menuService.getMenuHierarchyByRoles(MenuType.AdminConsole, loginUser.getRoles());
            mav.addObject("menus", menus);
            mav.addObject("notiCnt", notificationService.getNotificationCntByUsername(loginUser.getUsername()));
            mav.addObject("notifications", notificationService.getNotificationByUsername(loginUser.getUsername()));
        }
    }
}
