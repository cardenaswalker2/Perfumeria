package com.profumi.profumi.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        System.out.println("Login exitoso. Usuario: " + authentication.getName() + " | Roles: " + roles);

        if (roles.contains("ROLE_ADMIN")) {
            System.out.println("Redirigiendo a Dashboard de Admin");
            response.sendRedirect("/admin/dashboard");
        } else if (roles.contains("ROLE_ASESOR")) {
            System.out.println("Redirigiendo a Dashboard de Asesor");
            response.sendRedirect("/asesor/dashboard");
        } else {
            System.out.println("Redirigiendo a Home (Cliente)");
            response.sendRedirect("/");
        }
    }
}
