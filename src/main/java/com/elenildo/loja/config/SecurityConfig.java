package com.elenildo.loja.config;

import com.elenildo.loja.enums.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
//                .csrf(AbstractHttpConfigurer::disable)

                .logout(config -> config.logoutSuccessUrl("/"))
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/", "/home").permitAll();
                    authorize.requestMatchers("/login").permitAll();
                    authorize.requestMatchers("/logout").permitAll();
                    authorize.requestMatchers("/register").permitAll();
                    authorize.requestMatchers("/contato").permitAll();
                    authorize.requestMatchers("/admin/**")
                            .hasAnyRole(UserRole.ADMIN.name(), UserRole.MANAGER.name());
                    authorize.anyRequest().authenticated();
                })
                .formLogin(configurer -> {
                    configurer
                            .loginPage("/login")
                            .defaultSuccessUrl("/", true)
                            .failureUrl("/login-error");
                })
                .build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
