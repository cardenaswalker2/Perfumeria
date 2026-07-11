package com.profumi.profumi.config;

import com.profumi.profumi.services.CustomOAuth2UserService;
import com.profumi.profumi.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, 
                                                 CustomOAuth2UserService customOAuth2UserService, 
                                                 CustomUserDetailsService customUserDetailsService,
                                                 CustomAuthenticationSuccessHandler successHandler) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/asesor/**").hasAnyRole("ADMIN", "ASESOR")
                .requestMatchers("/carrito/pago", "/carrito/finalizar").authenticated()
                .requestMatchers(
                    "/", "/css/**", "/js/**", "/images/**", "/uploads/**",
                    "/catalogo", "/producto/**",
                    "/diagnostico", "/api/diagnostico/**",
                    "/kits", "/buscar",
                    "/login", "/registro", "/recuperar-password", "/login/oauth2/**", "/oauth2/**",
                    "/carrito/**", "/api/chat/**", "/error"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler(successHandler)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/login")
                .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                .successHandler(successHandler)
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
            )
            .rememberMe(rm -> rm
                .key("profumiSecretKey")
                .rememberMeParameter("remember-me")
                .tokenValiditySeconds(86400 * 30) // 30 days
                .userDetailsService(customUserDetailsService)
            );

        return http.build();
    }
}
