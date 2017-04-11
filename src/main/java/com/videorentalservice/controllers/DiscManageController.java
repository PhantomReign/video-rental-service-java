package com.videorentalservice.controllers;

import com.videorentalservice.models.Disc;
import com.videorentalservice.services.CategoryService;
import com.videorentalservice.services.DiscService;
import com.videorentalservice.services.GenreService;
import com.videorentalservice.utilities.SecurityUtility;
import com.videorentalservice.validators.DiscValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @RequestMapping("movie/edit/{id}")
    public String editDisc(@PathVariable Integer id, Model model){
        model.addAttribute("disc", discService.getById(id));
        return "disc/disc-form-update";
    }

    @RequestMapping("movie/new")
    public String newDisc(Model model){
        model.addAttribute("disc", new Disc());
        return "disc/disc-form-create";
    }


    @RequestMapping(value = "/movie/new", method = RequestMethod.POST)
    public String saveDisc(@Valid @ModelAttribute("disc") Disc disc, BindingResult result,
                           RedirectAttributes redirectAttributes){
        discValidator.validate(disc, result);
        if(result.hasErrors()){
            return "disc/disc-form-create";
        }
        discService.save(disc);
        redirectAttributes.addFlashAttribute("info", "Disk bol úspešne vytvorený.");
        return "redirect:/movies";
    }

    @RequestMapping(value = "/movie/edit/{id}", method = RequestMethod.POST)
    public String updateDisc(@Valid @ModelAttribute("disc") Disc disc,
                             BindingResult result,
                             RedirectAttributes redirectAttributes){
        if(result.hasErrors()){
            return "disc/disc-form-update";
        }
        discService.update(disc);
        redirectAttributes.addFlashAttribute("info", "Disk bol úspešne upravený.");
        return "redirect:/movies";
    }


    @RequestMapping("movie/delete/{id}")
    public String deleteDisc(@PathVariable Integer id,
                             RedirectAttributes redirectAttributes){
        discService.delete(id);
        redirectAttributes.addFlashAttribute("info", "Disk bol úspešne vymazaný.");
        return "redirect:/movies";
    }
}
