package com.elenildo.loja.controller;

import com.elenildo.loja.dto.ProductDto;
import com.elenildo.loja.service.CategoryService;
import com.elenildo.loja.service.ProductService;
import com.opencsv.exceptions.CsvException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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

    @GetMapping("update/{id}")
    public String update(@PathVariable Long id, ModelMap model, RedirectAttributes attributes) {
        var product = productService.findById(id);
        model.addAttribute("categories", categoryService.getAll()); //cachear isso

        if(product.isEmpty()){
            attributes.addFlashAttribute("error", "ID inválido");
            return "redirect:/admin/products";
        }
        model.addAttribute(new ProductDto(product.get()));
        return "admin/products/create-product";
    }

    @GetMapping("create")
    public ModelAndView create() {
        var mv = new ModelAndView("admin/products/create-product");
        mv.addObject("productDto", new ProductDto());
        mv.addObject("categories", categoryService.getAll());
        return mv;
    }

    @PostMapping
    public String save(@Valid ProductDto productDto, BindingResult result, @RequestParam("images") MultipartFile[] images, Model model) {
        var product = productService.findByTitleIgnoreCase(productDto.getTitle().trim());
        if(product.isPresent() && !product.get().getId().equals(productDto.getId()))
            result.addError(new FieldError("productDto", "title", "Já existe um produto com este título"));

        if(result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll()); //Reloads category list
            return "admin/products/create-product";
        }
        Set<String> previousImgList = new HashSet<>();

        if(product.isPresent()) {
            if(product.get().getImages() != null)
                previousImgList = product.get().getImages();
        }

        try {
            productService.create(productDto, previousImgList);
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

    @PostMapping("csv")
    public String uploadCsv(@RequestParam("file") MultipartFile file, Model model) {
        try {
            productService.saveCsvFile(file);
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/admin/products";
    }

    @GetMapping("remove/{id}")
    public String remove(@PathVariable Long id,RedirectAttributes attributes) {
        var product = productService.findById(id);
        if(product.isEmpty()) {
            attributes.addFlashAttribute("error", "ID inválido");
            return "redirect:/admin/products";
        }
        productService.remove(id);
        attributes.addFlashAttribute("message", "Removido com sucesso.");
        return "redirect:/admin/products";
    }
}
