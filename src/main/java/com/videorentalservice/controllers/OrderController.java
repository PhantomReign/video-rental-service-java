package com.videorentalservice.controllers;

import com.videorentalservice.models.Cart;
import com.videorentalservice.models.Disc;
import com.videorentalservice.models.PreOrderStats;
import com.videorentalservice.services.DiscService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;

/**
 * Created by Rave on 18.02.2017.
 */

@Controller

public class OrderController {

    private DiscService discService;

    @Autowired
    public void setDiscService(DiscService discService) {
        this.discService = discService;
    }


    @RequestMapping(value = "/order/checkout", method = RequestMethod.GET)
    public String listCart(Model model,
                           HttpServletRequest request) {

        Cart cart;
        cart = (Cart) request.getSession().getAttribute("CART_KEY");
        if(cart == null){
            cart = new Cart();
            request.getSession().setAttribute("CART_KEY", cart);
        }
        model.addAttribute("cart", cart.getItems());


        PreOrderStats stats;
        stats = (PreOrderStats) request.getSession().getAttribute("ORDER_STATS");
        if(stats == null)
            stats = new PreOrderStats();

        BigDecimal total_price = new BigDecimal(0);
        for (Disc tmp: cart.getItems() ) {
            total_price = total_price.add(tmp.getPrice());
        }

        total_price = total_price.multiply(stats.getTotal_days());

        stats.setTotal_discs(new BigDecimal(cart.getItems().size()));
        stats.setPrice(total_price);
        request.getSession().setAttribute("ORDER_STATS", stats);
        model.addAttribute("stats", stats);
        return "order/order";
    }

    @RequestMapping(value = "/order/dayAdd", method = RequestMethod.GET)
    public String dayAdd(
            HttpServletRequest request,
            RedirectAttributes redirectAttributes) {

        PreOrderStats stats;
        stats = (PreOrderStats) request.getSession().getAttribute("ORDER_STATS");
        if(stats == null)
            stats = new PreOrderStats();

        stats.addDay();

        request.getSession().setAttribute("ORDER_STATS", stats);
        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }

    @RequestMapping(value = "/order/dayRemove", method = RequestMethod.GET)
    public String dayRemove(
            HttpServletRequest request,
            RedirectAttributes redirectAttributes) {

        PreOrderStats stats;
        stats = (PreOrderStats) request.getSession().getAttribute("ORDER_STATS");
        if(stats == null)
            stats = new PreOrderStats();

        if (!stats.removeDay())
            redirectAttributes.addFlashAttribute("info", "Deň nemohol byť nastavený");

        request.getSession().setAttribute("ORDER_STATS", stats);
        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }


}
