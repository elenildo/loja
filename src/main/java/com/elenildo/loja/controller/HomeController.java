package com.elenildo.loja.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"","home"})
    public String index() {
        return "index";
    }

    @GetMapping("contato")
    public String contato() {
        return "contato";
    }
}
