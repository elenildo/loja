package com.elenildo.loja.controller;

import com.elenildo.loja.dto.ProductDto;
import com.elenildo.loja.service.CategoryService;
import com.elenildo.loja.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("admin/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping
    public ModelAndView index() {
        var mv = new ModelAndView("admin/products/index");
        return mv.addObject("productDto", new ProductDto());
    }

    @GetMapping("datatables/products")
    public ResponseEntity<?> getProducts(HttpServletRequest request) {
        return ResponseEntity.ok(productService.getProducts(request));
    }

    @GetMapping("create")
    public ModelAndView create() {
        var mv = new ModelAndView("admin/products/create-product");
        mv.addObject("productDto", new ProductDto());
        mv.addObject("categories", categoryService.getAll());
        return mv;
    }

    @PostMapping
    public String save(@Valid ProductDto productDto, @RequestParam("images") MultipartFile[] images, BindingResult result, Model model) {
        if(productService.productExists(productDto.getTitle().trim()))
            result.addError(new FieldError("productDto", "title", "Já existe um produto com este título"));

        if(result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll()); //Reloads category list
            return "admin/products/create-product";
        }
        try {
            productService.create(productDto, images);
        }catch (Exception e) {
            model.addAttribute("categories", categoryService.getAll()); //Reloads category list
            result.addError(new FieldError(
                    "productDto",
                    "images",
                    "Erro ao fazer upload de imagens" + e.getMessage()));
            return "admin/products/create-product";
        }
        return "redirect:/admin/products";
    }
}
