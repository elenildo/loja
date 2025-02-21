package com.elenildo.loja.controller;

import com.elenildo.loja.dto.CategoryDto;
import com.elenildo.loja.service.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public ResponseEntity<?> getCategories(HttpServletRequest request) {
        return ResponseEntity.ok(categoryService.getCategories(request));
    }

    @GetMapping("create")
    public ModelAndView create() {
        var mv = new ModelAndView("admin/categories/create-category");
        return mv.addObject("categoryDto", new CategoryDto());
    }

    @GetMapping("update/{id}")
    public String update(@PathVariable Long id, ModelMap model, RedirectAttributes attributes) {
        var category = categoryService.findById(id);
        if(category.isEmpty()){
            attributes.addFlashAttribute("error", "ID inválido");
            return "redirect:/admin/categories";
        }
        model.addAttribute(new CategoryDto(category.get()));
        return "admin/categories/create-category";
    }

    @GetMapping("remove/{id}")
    public String remove(@PathVariable Long id,RedirectAttributes attributes) {
        var category = categoryService.findById(id);
        if(category.isEmpty()) {
            attributes.addFlashAttribute("error", "ID inválido");
            return "redirect:/admin/categories";
        }
        try {
            categoryService.remove(id);
        }catch (Exception e) {
            attributes.addFlashAttribute(
                    "error",
                    "Não foi possível remover. Esta categoria está associada a um ou mais produtos"
            );
            return "redirect:/admin/categories";
        }
        attributes.addFlashAttribute("message", "Removido com sucesso.");
        return "redirect:/admin/categories";
    }

    @PostMapping
    public String save(@Valid CategoryDto categoryDto, BindingResult result) {
        var category = categoryService.findByNameIgnoreCase(categoryDto.getName().trim());
        if(category.isPresent() && !category.get().getId().equals(categoryDto.getId()))
            result.addError(new FieldError("categoryDto", "name", "Já existe uma categoria com este nome"));

        if(result.hasErrors())
            return "admin/categories/create-category";

        categoryService.create(categoryDto);
        return "redirect:/admin/categories";

    }

}
