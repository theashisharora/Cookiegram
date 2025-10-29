package com.cookiegram.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails customer = User
                .withUsername("customer")
                .password("pass")
                .roles("CUSTOMER")
                .build();

        UserDetails employee = User
                .withUsername("employee")
                .password("pass")
                .roles("EMPLOYEE")
                .build();

        UserDetails admin = User
                .withUsername("admin")
                .password("pass")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(customer, employee, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationSuccessHandler roleBasedSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(
                    HttpServletRequest request,
                    HttpServletResponse response,
                    org.springframework.security.core.Authentication authentication
            ) throws IOException, ServletException {

                var auth = authentication.getAuthorities().toString();

                if (auth.contains("ROLE_ADMIN")) {
                    response.sendRedirect("/admin");
                } else if (auth.contains("ROLE_EMPLOYEE")) {
                    response.sendRedirect("/employee");
                } else if (auth.contains("ROLE_CUSTOMER")) {
                    response.sendRedirect("/customer");
                } else {
                    response.sendRedirect("/");
                }
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthenticationSuccessHandler successHandler
    ) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/", "/api/promotions/**", "/css/**", "/js/**", "/images/**").permitAll()
                    .requestMatchers("/customer").hasRole("CUSTOMER")
                    .requestMatchers("/employee").hasRole("EMPLOYEE")
                    .requestMatchers("/admin").hasRole("ADMIN")
                    .anyRequest().authenticated()
            )
            .formLogin(form -> form
                    .loginPage("/login")
                    .permitAll()
                    .successHandler(successHandler)
            )
            .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .permitAll()
            )
            .exceptionHandling(e -> e
                    .accessDeniedPage("/403")
            );

        return http.build();
    }
}
