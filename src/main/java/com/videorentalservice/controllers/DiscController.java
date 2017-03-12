package com.videorentalservice.controllers;

import com.videorentalservice.models.Disc;
import com.videorentalservice.services.DiscService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Rave on 18.02.2017.
 */

@Controller
public class DiscController {
    private DiscService discService;

    @Autowired
    public void setDiscService(DiscService discService) {
        this.discService = discService;
    }

    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    public String list(Model model){
        model.addAttribute("discs", discService.listAll());
        return "discs";
    }

    @RequestMapping("movie/show/{id}")
    public String showDisc(@PathVariable Integer id, Model model){
        model.addAttribute("disc", discService.getById(id));
        return "disc-show";
    }

    @RequestMapping("movie/edit/{id}")
    public String editDisc(@PathVariable Integer id, Model model){
        model.addAttribute("disc", discService.getById(id));
        return "disc-form";
    }

    @RequestMapping("movie/new")
    public String newDisc(Model model){
        model.addAttribute("disc", new Disc());
        return "disc-form";
    }

    @RequestMapping(value = "movie", method = RequestMethod.POST)
    public String saveDisc(Disc disc){
        discService.saveOrUpdate(disc);
        return "redirect:/movie/" + disc.getId();
    }

    @RequestMapping("movie/delete/{id}")
    public String deleteDisc(@PathVariable Integer id){
        discService.delete(id);
        return "redirect:/movies";
    }
}
