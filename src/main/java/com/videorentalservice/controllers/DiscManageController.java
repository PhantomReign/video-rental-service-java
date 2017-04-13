package com.videorentalservice.controllers;

import com.querydsl.core.types.Predicate;
import com.videorentalservice.models.Disc;
import com.videorentalservice.services.CategoryService;
import com.videorentalservice.services.DiscService;
import com.videorentalservice.services.GenreService;
import com.videorentalservice.utilities.SecurityUtility;
import com.videorentalservice.validators.DiscValidator;
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
@Secured(SecurityUtility.MANAGE_DISC)
public class DiscManageController {

    private DiscService discService;
    private CategoryService categoryService;
    private GenreService genreService;

    @Autowired
    private DiscValidator discValidator;

    @Autowired
    public void setDiscService(DiscService discService) {
        this.discService = discService;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setGenreService(GenreService genreService) {
        this.genreService = genreService;
    }

    @ModelAttribute("allCategories")
    public List<?> categoriesList(){
        return categoryService.listAll();
    }

    @ModelAttribute("allGenres")
    public List<?> genresList(){
        return genreService.listAll();
    }


    @RequestMapping(value = "/admin/movies", method = RequestMethod.GET)
    public String listAdminDiscs(Model model,
                            @QuerydslPredicate(root = Disc.class) Predicate predicate,
                            @PageableDefault(sort = { "title", "originalTitle" }, value = 8) Pageable pageable,
                            @RequestParam MultiValueMap<String, String> parameters) {

        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequest();
        builder.replaceQueryParam("page");

        model.addAttribute("discs", discService.findAll(predicate, pageable));
        model.addAttribute("genres", genreService.listAll());
        model.addAttribute("categories", categoryService.listAll());

        return "disc/discs-admin";
    }


    @RequestMapping("admin/movie/edit/{id}")
    public String editDisc(@PathVariable Integer id, Model model){
        model.addAttribute("disc", discService.getById(id));
        return "disc/disc-form-update";
    }

    @RequestMapping("admin/movie/new")
    public String newDisc(Model model){
        model.addAttribute("disc", new Disc());
        return "disc/disc-form-create";
    }

    @RequestMapping("admin/movie/show/{id}")
    public String showDiscAdmin(@PathVariable Integer id, Model model){
        model.addAttribute("disc", discService.getById(id));
        return "disc/disc-show-admin";
    }

    @RequestMapping(value = "/admin/movie/new", method = RequestMethod.POST)
    public String saveDisc(@Valid @ModelAttribute("disc") Disc disc, BindingResult result,
                           RedirectAttributes redirectAttributes){
        discValidator.validate(disc, result);
        if(result.hasErrors()){
            return "disc/disc-form-create";
        }
        discService.save(disc);
        redirectAttributes.addFlashAttribute("info", "Disk bol úspešne vytvorený.");
        return "redirect:/admin/movies";
    }

    @RequestMapping(value = "/admin/movie/edit/{id}", method = RequestMethod.POST)
    public String updateDisc(@Valid @ModelAttribute("disc") Disc disc,
                             BindingResult result,
                             RedirectAttributes redirectAttributes){
        if(result.hasErrors()){
            return "disc/disc-form-update";
        }
        discService.update(disc);
        redirectAttributes.addFlashAttribute("info", "Disk bol úspešne upravený.");
        return "redirect:/admin/movies";
    }


    @RequestMapping("admin/movie/delete/{id}")
    public String deleteDisc(@PathVariable Integer id,
                             RedirectAttributes redirectAttributes){
        discService.delete(id);
        redirectAttributes.addFlashAttribute("info", "Disk bol úspešne vymazaný.");
        return "redirect:/admin/movies";
    }
}
