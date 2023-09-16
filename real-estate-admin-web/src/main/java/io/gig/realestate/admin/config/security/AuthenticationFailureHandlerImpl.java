package io.gig.realestate.admin.config.security;

import io.gig.realestate.domain.admin.AdministratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @author : JAKE
 * @date : 2023/02/28
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {

    private final AdministratorService administratorService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");
        String errorMessage = null;
        if (exception instanceof BadCredentialsException || exception instanceof InternalAuthenticationServiceException) {
            errorMessage = "계정 정보가 올바르지 않습니다.";
            administratorService.increasePasswordFailureCount(request.getParameter("username"));
        } else  if (exception instanceof LockedException) {
            errorMessage = "패스워드 5회 이상 틀려 계정이 잠겼습니다.";
        } else {
            errorMessage = "알 수 없는 문제가 발생하였습니다.";
        }

        request.setAttribute("exception", exception.getClass().getName().replace(exception.getClass().getPackageName() + ".", ""));
        request.setAttribute("exceptionMessage", errorMessage);

        log.error("Authentication fail username: {}, message: {}", username, exception);
//        request.getRequestDispatcher("/login?error=500").forward(request, response);

        response.sendRedirect(request.getContextPath() + "/login?error=500");
    }

}
