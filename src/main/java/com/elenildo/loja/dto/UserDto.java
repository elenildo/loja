package com.elenildo.loja.dto;

import com.elenildo.loja.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    @NotBlank
    private String name;

    private String lastname;

    @Email
    @NotBlank
    private String username;

    @NotBlank
    @Min(6)
    private String password;

    private String confirm;

    public UserDto(User user) {
        name = user.getName();
        lastname = user.getLastname();
        username = user.getUsername();
        password = user.getPassword();
    }
}
