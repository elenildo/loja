package com.elenildo.loja.controller;

import com.elenildo.loja.dto.UserDto;
import com.elenildo.loja.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @GetMapping("register")
    public ModelAndView register() {
        var mv = new ModelAndView("register");
        return mv.addObject("userDto", new UserDto());
    }

    @PostMapping("register")
    public String registerSave(@Valid UserDto userDto, BindingResult result) {
        if(! userDto.getConfirm().equals(userDto.getPassword()))
            result.addError(new FieldError("registerDto", "confirm", "As senhas não coincidem."));

        if(userService.userExists(userDto.getUsername().trim()))
            result.addError(new FieldError("userDto", "username", "Já existe um usuário com este e-mail"));

        if(result.hasErrors())
            return "register";

        userService.create(userDto);

        return "redirect:/login";
    }
}
