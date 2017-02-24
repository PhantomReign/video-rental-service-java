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

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    @RequestMapping(value = "/discs", method = RequestMethod.GET)
    public String list(Model model){
        model.addAttribute("discs", discService.listAll());
        return "discs";
    }

    @RequestMapping("disc/{id}")
    public String showDisc(@PathVariable Integer id, Model model){
        model.addAttribute("disc", discService.getById(id));
        return "disc-show";
    }

    @RequestMapping("disc/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
        model.addAttribute("disc", discService.getById(id));
        return "disc-form";
    }

    @RequestMapping("disc/new")
    public String newDisc(Model model){
        model.addAttribute("disc", new Disc());
        return "disc-form";
    }

    @RequestMapping(value = "disc", method = RequestMethod.POST)
    public String saveDisc(Disc disc){
        discService.saveOrUpdate(disc);
        return "redirect:/disc/" + disc.getId();
    }

    @RequestMapping("disc/delete/{id}")
    public String delete(@PathVariable Integer id){
        discService.delete(id);
        return "redirect:/discs";
    }
}
