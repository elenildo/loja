package com.elenildo.loja.controller;

import com.elenildo.loja.dto.ProductCardDto;
import com.elenildo.loja.dto.ProductDto;
import com.elenildo.loja.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
@RequestMapping("/")
public class HomeController {

    private final ProductService productService;

    @GetMapping
    public ModelAndView index() {
        var mv = new ModelAndView("index");
        return mv.addObject("products", productService.findAll()
                .stream().map(ProductCardDto::new).toList());
    }

    @GetMapping("contato")
    public String contato(ModelMap model) {
        return "contato";
    }

    @GetMapping("product/{id}")
    public String productDetail(@PathVariable Long id, Model model, RedirectAttributes attributes) {
        var prod = productService.findById(id);
        if(prod.isEmpty()) {
            attributes.addFlashAttribute("error", "Produto não encontrado");
            return "redirect:/";
        }
        model.addAttribute("product", new ProductDto(productService.findById(id).get()));

        return "product-detail";
    }
}
