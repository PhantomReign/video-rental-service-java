package com.videorentalservice.controllers;

import com.querydsl.core.types.Predicate;
import com.videorentalservice.controllers.abstracts.AbstractBaseController;
import com.videorentalservice.models.Genre;
import com.videorentalservice.services.GenreService;
import com.videorentalservice.services.PermissionService;
import com.videorentalservice.utilities.SecurityUtility;
import com.videorentalservice.validators.GenreValidator;
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
 * Created by Rave on 25.03.2017.
 */

@Controller
@Secured(SecurityUtility.MANAGE_GENRES)
public class GenreManageController extends AbstractBaseController {
    private GenreService genreService;

    @Autowired
    private GenreValidator genreValidator;


    @Autowired
    public void setGenreService(GenreService genreService) {
        this.genreService = genreService;
    }

    @RequestMapping(value = "/admin/genres", method = RequestMethod.GET)
    public String listCategories(Model model,
                                 @QuerydslPredicate(root = Genre.class) Predicate predicate,
                                 @PageableDefault(sort = { "id", "name" }, value = 10) Pageable pageable,
                                 @RequestParam MultiValueMap<String, String> parameters) {

        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequest();
        builder.replaceQueryParam("page");
        model.addAttribute("genres", genreService.findAll(predicate, pageable));
        return "genre/genres";
    }


    @RequestMapping("admin/genre/show/{id}")
    public String showGenre(@PathVariable Integer id, Model model){
        model.addAttribute("genre", genreService.getById(id));
        return "genre/genre-show";
    }

    @RequestMapping("admin/genre/edit/{id}")
    public String editGenre(@PathVariable Integer id, Model model){
        model.addAttribute("genre", genreService.getById(id));
        return "genre/genre-form-update";
    }

    @RequestMapping("admin/genre/new")
    public String newGenre(Model model){
        model.addAttribute("genre", new Genre());
        return "genre/genre-form-create";
    }

    @RequestMapping(value = "/admin/genre/new", method = RequestMethod.POST)
    public String saveGenre(@Valid @ModelAttribute("genre") Genre genre, BindingResult result,
                           RedirectAttributes redirectAttributes){

        genreValidator.validate(genre, result);
        if(result.hasErrors()){
            return "genre/genre-form-create";
        }
        genreService.save(genre);
        redirectAttributes.addFlashAttribute("info", "Žáner bol úspešne vytvorený.");
        return "redirect:/admin/genres";
    }

    @RequestMapping(value="/admin/genre/edit/{id}", method=RequestMethod.POST)
    public String updateGenre(@ModelAttribute("genre") Genre genre,
                             RedirectAttributes redirectAttributes){

        genreService.update(genre);
        redirectAttributes.addFlashAttribute("info", "Žáner bol úspešne upravený.");
        return "redirect:/admin/genres";
    }

    @RequestMapping("admin/genre/delete/{id}")
    public String deleteGenre(@PathVariable Integer id,
                             RedirectAttributes redirectAttributes){
        genreService.delete(id);
        redirectAttributes.addFlashAttribute("info", "Žáner bol úspešne vymazaný.");
        return "redirect:/admin/genres";
    }
}
