package com.elenildo.loja.config;

import com.elenildo.loja.enums.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
//                .cors(AbstractHttpConfigurer::disable)
//                .httpBasic(withDefaults())
                .logout(config -> config.logoutSuccessUrl("/"))
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/admin/**")
                            .hasAnyRole(UserRole.ADMIN.name(), UserRole.MANAGER.name());
                    authorize.requestMatchers("/user/profile/**").authenticated();
                    authorize.anyRequest().permitAll();
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
