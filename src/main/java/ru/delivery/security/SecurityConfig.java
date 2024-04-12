package ru.delivery.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import ru.delivery.service.CustomerService;

import java.io.IOException;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(requestMatcherRegistry -> requestMatcherRegistry
//                        .requestMatchers("/data-api/**")
//                        .hasRole("ADMIN")
//
//                        .requestMatchers("/api/**")
//                        .hasRole("ADMIN")

                        .requestMatchers("/ingredient_panel/**")
                        .hasRole("ADMIN")

                        .requestMatchers("/design/**", "/orders/**")
                        .hasRole("USER")

                        .requestMatchers("/", "/**", "/registrate")
                        .permitAll()
                )

                .csrf(
                        csrfConfigurer -> csrfConfigurer.disable())
                .headers(
                        headersConfigurer -> headersConfigurer.frameOptions(
                                frameOptionsConfig -> frameOptionsConfig.sameOrigin()))

                .formLogin(loginConfigurer -> {
                    loginConfigurer.permitAll();
                    loginConfigurer.loginPage("/login");
                    loginConfigurer.successHandler(new DesignSuccessHandler());
                })

                .logout(logoutConfigurer -> {
                    logoutConfigurer.permitAll();
                    logoutConfigurer.logoutUrl("/logout");
                    logoutConfigurer.logoutSuccessUrl("/");
                })

                .build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(CustomerService service) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(service);

        return provider;
    }

    private static class
    DesignSuccessHandler implements AuthenticationSuccessHandler {
        @Override
        public void onAuthenticationSuccess(HttpServletRequest request,
                                            HttpServletResponse response,
                                            Authentication authentication)
                throws IOException, ServletException {
            request.getSession().setAttribute("user", authentication.getPrincipal());
            response.sendRedirect("/design");
        }
    }
}
