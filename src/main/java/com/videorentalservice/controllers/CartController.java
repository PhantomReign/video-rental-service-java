package com.videorentalservice.controllers;

import com.querydsl.core.types.Predicate;
import com.videorentalservice.models.Cart;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Rave on 18.02.2017.
 */

@Controller

public class CartController {

    private DiscService discService;

    @Autowired
    public void setDiscService(DiscService discService) {
        this.discService = discService;
    }


    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public String listCart(Model model,
                           HttpServletRequest request) {

        Cart cart;
        cart = (Cart) request.getSession().getAttribute("CART_KEY");
        if(cart == null){
            cart = new Cart();
            request.getSession().setAttribute("CART_KEY", cart);
        }
        model.addAttribute("cart", cart.getItems());
        return "cart/cart";
    }

    @RequestMapping("cart/add/{id}")
    public String addToCart(@PathVariable Integer id,
                             RedirectAttributes redirectAttributes,
                            HttpServletRequest request){

        Cart cart;
        cart = (Cart) request.getSession().getAttribute("CART_KEY");
        if(cart == null){
            cart = new Cart();
        }
        Disc tmp = discService.getById(id);
        if (cart.addItem(tmp))
            redirectAttributes.addFlashAttribute("info", "Disk bol úspešne pridaný do košíka.");
        else
            redirectAttributes.addFlashAttribute("info", "Disk už máte v košíku.");
        request.getSession().setAttribute("CART_KEY", cart);
        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }

    @RequestMapping("cart/remove/{id}")
    public String removeFromCart(@PathVariable Integer id,
                            RedirectAttributes redirectAttributes,
                            HttpServletRequest request){
        Cart cart;
        cart = (Cart) request.getSession().getAttribute("CART_KEY");
        if(cart == null){
            cart = new Cart();
        }
        Disc tmp = discService.getById(id);
        cart.removeItem(tmp);
        request.getSession().setAttribute("CART_KEY", cart);
        redirectAttributes.addFlashAttribute("info", "Disk bol úspešne odstránený z košíka.");
        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }

}
