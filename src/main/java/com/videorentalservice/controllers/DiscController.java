package com.videorentalservice.controllers;

import com.querydsl.core.types.Predicate;
import com.videorentalservice.models.Disc;
import com.videorentalservice.services.CategoryService;
import com.videorentalservice.services.DiscService;
import com.videorentalservice.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * Created by Rave on 18.02.2017.
 */

@Controller

public class DiscController {
    private DiscService discService;
    private GenreService genreService;
    private CategoryService categoryService;

    @Autowired
    public void setDiscService(DiscService discService) {
        this.discService = discService;
    }

    @Autowired
    public void setGenreService(GenreService genreService) {
        this.genreService = genreService;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    public String listDiscs(Model model,
                       @QuerydslPredicate(root = Disc.class) Predicate predicate,
                       @PageableDefault(sort = { "title", "originalTitle" }, value = 8) Pageable pageable,
                       @RequestParam MultiValueMap<String, String> parameters) {

        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequest();
        builder.replaceQueryParam("page");

        model.addAttribute("discs", discService.findAll(predicate, pageable));
        model.addAttribute("genres", genreService.listAll());
        model.addAttribute("categories", categoryService.listAll());

        return "disc/discs";
    }



    @RequestMapping("movie/show/{id}")
    public String showDisc(@PathVariable Integer id, Model model){
        model.addAttribute("disc", discService.getById(id));
        return "disc/disc-show";
    }
}
