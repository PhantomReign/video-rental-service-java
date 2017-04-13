package com.videorentalservice.controllers;

import com.querydsl.core.types.Predicate;
import com.videorentalservice.controllers.abstracts.AbstractBaseController;
import com.videorentalservice.models.Category;
import com.videorentalservice.services.CategoryService;
import com.videorentalservice.utilities.SecurityUtility;
import com.videorentalservice.validators.CategoryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Rave on 24.03.2017.
 */

@Controller
@Secured(SecurityUtility.MANAGE_CATEGORIES)
public class CategoryManageController extends AbstractBaseController {
    private CategoryService categoryService;

    @Autowired
    private CategoryValidator categoryValidator;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "/admin/categories", method = RequestMethod.GET)
    public String listCategories(Model model,
                            @QuerydslPredicate(root = Category.class) Predicate predicate,
                            @PageableDefault(sort = { "id", "name" }, value = 10) Pageable pageable,
                            @RequestParam MultiValueMap<String, String> parameters) {

        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequest();
        builder.replaceQueryParam("page");

        model.addAttribute("categories", categoryService.findAll(predicate, pageable));
        return "category/categories";
    }

    @RequestMapping("admin/category/show/{id}")
    public String showCategory(@PathVariable Integer id, Model model){
        model.addAttribute("category", categoryService.getById(id));
        return "category/category-show";
    }

    @RequestMapping("admin/category/edit/{id}")
    public String editCategory(@PathVariable Integer id, Model model){
        model.addAttribute("category", categoryService.getById(id));
        return "category/category-form-update";
    }

    @RequestMapping("admin/category/new")
    public String newCategory(Model model){
        model.addAttribute("category", new Category());
        return "category/category-form-create";
    }

    @RequestMapping(value = "/admin/category/new", method = RequestMethod.POST)
    public String saveCategory(@Valid @ModelAttribute("category") Category category,
                           BindingResult result,
                           RedirectAttributes redirectAttributes){

        categoryValidator.validate(category, result);
        if(result.hasErrors()){
            return "category/category-form-create";
        }
        categoryService.save(category);
        redirectAttributes.addFlashAttribute("info", "Kategória bola úspešne vytvorená.");
        return "redirect:/admin/categories";
    }

    @RequestMapping(value="/admin/category/edit/{id}", method=RequestMethod.POST)
    public String updateCategory(@ModelAttribute("category") Category category,
                             RedirectAttributes redirectAttributes){

        categoryService.update(category);
        redirectAttributes.addFlashAttribute("info", "Kategória bola úspešne upravená.");
        return "redirect:/admin/categories";
    }

    @RequestMapping("admin/category/delete/{id}")
    public String deleteCategory(@PathVariable Integer id,
                             RedirectAttributes redirectAttributes){
        categoryService.delete(id);
        redirectAttributes.addFlashAttribute("info", "Kategória bola úspešne vymazaná.");
        return "redirect:/admin/categories";
    }
}
