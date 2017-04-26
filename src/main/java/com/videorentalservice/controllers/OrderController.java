package com.videorentalservice.controllers;

import com.videorentalservice.controllers.abstracts.OrderNumberGenerator;
import com.videorentalservice.models.Cart;
import com.videorentalservice.models.Disc;
import com.videorentalservice.models.Order;
import com.videorentalservice.models.PreOrderStats;
import com.videorentalservice.models.abstracts.OrderStates;
import com.videorentalservice.services.DiscService;
import com.videorentalservice.services.OrderService;
import com.videorentalservice.services.UserService;
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
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Rave on 18.02.2017.
 */

@Controller

public class OrderController {

    private DiscService discService;
    private UserService userService;
    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setDiscService(DiscService discService) {
        this.discService = discService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping(value = "/order/checkout", method = RequestMethod.GET)
    public String listCart(Model model,
                           HttpServletRequest request) {

        Cart cart;
        cart = (Cart) request.getSession().getAttribute("CART_KEY");
        if (cart == null) {
            cart = new Cart();
            request.getSession().setAttribute("CART_KEY", cart);
        }
        model.addAttribute("cart", cart.getItems());


        PreOrderStats stats;
        stats = (PreOrderStats) request.getSession().getAttribute("ORDER_STATS");
        if (stats == null)
            stats = new PreOrderStats();

        BigDecimal total_price = new BigDecimal(0);
        for (Disc tmp : cart.getItems()) {
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
        if (stats == null)
            stats = new PreOrderStats();

        stats.addDay();

        request.getSession().setAttribute("ORDER_STATS", stats);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @RequestMapping(value = "/order/dayRemove", method = RequestMethod.GET)
    public String dayRemove(
            HttpServletRequest request,
            RedirectAttributes redirectAttributes) {

        PreOrderStats stats;
        stats = (PreOrderStats) request.getSession().getAttribute("ORDER_STATS");
        if (stats == null)
            stats = new PreOrderStats();

        if (!stats.removeDay())
            redirectAttributes.addFlashAttribute("info", "Deň nemohol byť nastavený");

        request.getSession().setAttribute("ORDER_STATS", stats);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @RequestMapping(value = "/order/approve", method = RequestMethod.GET)
    public String orderApprove(Model model,
                               HttpServletRequest request,
                               RedirectAttributes redirectAttributes,
                               Principal principal) {

        PreOrderStats stats;
        stats = (PreOrderStats) request.getSession().getAttribute("ORDER_STATS");
        if (stats == null) {
            redirectAttributes.addFlashAttribute("info", "Prázdna objednávka");
            String referer = request.getHeader("Referer");
            return "redirect:" + referer;
        }


        Cart cart;
        cart = (Cart) request.getSession().getAttribute("CART_KEY");
        if (cart == null) {
            redirectAttributes.addFlashAttribute("info", "Prázdny košík objednávky");
            String referer = request.getHeader("Referer");
            return "redirect:" + referer;
        }

        Order objednavka = new Order();
        objednavka.setToDate(stats.getToDate());
        objednavka.setFromDate(stats.getFromDate());
        objednavka.setDiscs(cart.getItems());
        objednavka.setPrice(stats.getPrice());
        objednavka.setStatus(OrderStates.READY);
        objednavka.setUser(userService.getByUserName(principal.getName()));
        objednavka.setOrderNumber(new OrderNumberGenerator().nextSessionId());
        objednavka.setTotal_days(stats.getTotal_days());

        // persist
        orderService.save(objednavka);
        model.addAttribute("order", objednavka);
        request.getSession().setAttribute("ORDER_STATS", null);
        request.getSession().setAttribute("CART_KEY", null);
        return "order/orderSummary";
    }

    @RequestMapping(value = "/order/orderSummary", method = RequestMethod.GET)
    public String summary(Model model,
                          HttpServletRequest request,
                          RedirectAttributes redirectAttributes,
                          Principal principal) {
        return "order/orderSummary";
    }

}
