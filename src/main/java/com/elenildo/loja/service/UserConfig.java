package com.elenildo.loja.service;

import com.elenildo.loja.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserConfig implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsernameIgnoreCase(username);
        return user.map(value -> User.withUsername(value.getUsername())
                .password(value.getPassword())
                .roles(value.getRoles().toArray(new String[value.getRoles().size()]))
                .build()).orElse(null);

    }
}
