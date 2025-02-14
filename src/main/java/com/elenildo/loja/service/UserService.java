package com.elenildo.loja.service;

import com.elenildo.loja.dto.UserDto;
import com.elenildo.loja.enums.UserRole;
import com.elenildo.loja.model.User;
import com.elenildo.loja.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public boolean userExists(String username) {
        return userRepository.existsByUsernameIgnoreCase(username);
    }

    public void create(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName().trim());
        user.setLastname(userDto.getLastname());
        user.setUsername(userDto.getUsername().trim());
        user.setPassword(encoder.encode(userDto.getPassword().trim()));
        user.setRoles(
                user.getUsername().equals("admin@teste.com") ?
                Set.of(UserRole.ADMIN.name(), UserRole.MANAGER.name()) :
                Set.of(UserRole.USER.name())
        );

        userRepository.save(user);
    }
}
