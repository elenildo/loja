package com.elenildo.loja.controller;

import com.elenildo.loja.dto.ProductCardDto;
import com.elenildo.loja.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
public class HomeController {

    private final ProductService productService;

    @GetMapping("/")
    public ModelAndView index() {
        var mv = new ModelAndView("index");
        return mv.addObject("products", productService.findAll()
                .stream().map(ProductCardDto::new).toList());
    }

    @GetMapping("contato")
    public String contato(ModelMap model) {
        return "contato";
    }
}
