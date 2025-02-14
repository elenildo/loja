package com.elenildo.loja.controller;

import com.elenildo.loja.dto.CategoryDto;
import com.elenildo.loja.service.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("admin/categories")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ModelAndView index() {
        var mv = new ModelAndView("admin/categories/index");
        return mv.addObject("category", new CategoryDto());
    }

    @GetMapping("datatables/categories")
    public ResponseEntity<?> getEspecialidades(HttpServletRequest request) {
        return ResponseEntity.ok(categoryService.getCategories(request));
    }

    @GetMapping("create")
    public ModelAndView create() {
        var mv = new ModelAndView("admin/categories/create-category");
        return mv.addObject("category", new CategoryDto());
    }

    @PostMapping
    public String save(@Valid CategoryDto categoryDto, BindingResult result) {
        if(categoryService.categoryExists(categoryDto.getName().trim()))
            result.addError(new FieldError("categoryDto", "name", "Já existe uma categoria com este nome"));

        if(result.hasErrors())
            return "admin/categories/create-category";

        categoryService.create(categoryDto);

        return "redirect:/admin/categories";

    }

}
