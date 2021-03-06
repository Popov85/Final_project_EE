package com.goit.popov.restaurant.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by Andrey on 1/7/2017.
 */
public class MainDispatcher implements AuthenticationSuccessHandler {

        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(MainDispatcher.class);
    
        private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, 
                                            Authentication authentication) throws IOException, ServletException {
                handle(request, response, authentication);
                clearAuthenticationAttributes(request);
        }

        private void handle(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException {
                String targetUrl = determineTargetUrl(authentication);
                if (response.isCommitted()) {
                        LOGGER.debug("Response has already been committed. Unable to redirect to " + targetUrl);
                        return;
                }
                redirectStrategy.sendRedirect(request, response, targetUrl);
        }

        private String determineTargetUrl(Authentication authentication) {
                boolean isWaiter = false;
                boolean isChef = false;
                boolean isManager = false;
                boolean isEmployee = false;
                Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
                for (GrantedAuthority grantedAuthority : authorities) {
                        if (grantedAuthority.getAuthority().equals("ROLE_WAITER")) {
                                isWaiter = true;
                                break;
                        } else if (grantedAuthority.getAuthority().equals("ROLE_CHEF")) {
                                isChef = true;
                                break;
                        } else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
                                isManager = true;
                                break;
                        } else if (grantedAuthority.getAuthority().equals("ROLE_EMPLOYEE")) {
                                isEmployee = true;
                                break;
                        } else throw new RuntimeException("Undefined role!");
                }
                if (isWaiter) {
                        LOGGER.info("ROLE_WAITER identified!");
                        return "/waiter/orders/today";
                } else if (isChef) {
                        LOGGER.info("ROLE_CHEF identified!");
                        return "/chef/prepared_dishes";
                } else if (isManager) {
                        LOGGER.info("ROLE_ADMIN identified!");
                        return "/admin";
                } else if (isEmployee) {
                        LOGGER.info("ROLE_EMPLOYEE identified!");
                        return "/";
                } else {
                        LOGGER.error("No ROLE identified!");
                        throw new IllegalStateException();
                }
        }

        private void clearAuthenticationAttributes(HttpServletRequest request) {
                HttpSession session = request.getSession(false);
                if (session == null) {
                        return;
                }
                session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }

}
