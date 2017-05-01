package com.videorentalservice.controllers;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.videorentalservice.VRSException;
import com.videorentalservice.controllers.abstracts.AbstractBaseController;
import com.videorentalservice.controllers.abstracts.OrderNumberGenerator;
import com.videorentalservice.models.*;
import com.videorentalservice.models.abstracts.OrderStates;
import com.videorentalservice.services.DiscService;
import com.videorentalservice.services.OrderService;
import com.videorentalservice.services.UserService;
import com.videorentalservice.services.common.EmailServiceImplementation;
import com.videorentalservice.view.MyPdfView;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Created by Rave on 18.02.2017.
 */

@Controller

public class OrderController extends AbstractBaseController {

    private DiscService discService;
    private UserService userService;
    private OrderService orderService;

    @Autowired
    private EmailServiceImplementation emailService;

    @Autowired
    private TemplateEngine templateEngine;

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


    private void sendOrderConfirmationEmail(Order order)
    {
        try {

            // Prepare the evaluation context
            final Context ctx = new Context();
            ctx.setVariable("orderNumber", order.getOrderNumber());

            // Create the HTML body using Thymeleaf
            final String htmlContent = this.templateEngine.process("email/orderConfirmationEmail", ctx);

            emailService.sendEmail(order.getUser().getEmail(),
                    "Videopožičovňa Saturn - Prijatie objednávky",
                    htmlContent);
        } catch (VRSException ignored) {
        }
    }


    @RequestMapping(value = "/account/orders", method = RequestMethod.GET)
    public String listUserOrders(Model model,
                                  @QuerydslPredicate(root = Order.class) Predicate predicate, Principal principal,
                                  @PageableDefault(sort = { "id", "orderNumber", "price", "fromDate", "toDate", "status" }, value = 10) Pageable pageable,
                                  @RequestParam MultiValueMap<String, String> parameters) {

        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequest();
        builder.replaceQueryParam("page");

        User user = userService.getByUserName(principal.getName());
        QOrder qOrder = QOrder.order;
        BooleanExpression isUser = qOrder.user.eq(user);

        model.addAttribute("orders", orderService.findAll(isUser, pageable));

        return "order/orders";
    }

    @RequestMapping("account/order/show/{id}")
    public String showOrderUser(@PathVariable Integer id, Model model, Principal principal){
        User user = userService.getByUserName(principal.getName());
        Order order = orderService.getById(id);
        if (!Objects.equals(order.getUser().getId(), user.getId())) {
            return "order/orders";
        }
        model.addAttribute("order", orderService.getById(id));
        return "order/order-show";
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
        objednavka.setStatus(OrderStates.NEW);
        objednavka.setUser(userService.getByUserName(principal.getName()));
        objednavka.setOrderNumber(new OrderNumberGenerator().nextSessionId());
        objednavka.setTotal_days(stats.getTotal_days());

        // persist
        orderService.save(objednavka);
        sendOrderConfirmationEmail(objednavka);

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

    @RequestMapping(value = "/order/downloadPdf/{id}", method = RequestMethod.GET)
    public ModelAndView downloadPdf(@PathVariable Integer id, Principal principal) {

        User user = userService.getByUserName(principal.getName());
        Order order = orderService.getById(id);
        if (order == null) {
            return new ModelAndView("security/forbidden");
        } else {
            if (!Objects.equals(order.getUser().getId(), user.getId())) {
                return new ModelAndView("security/forbidden");
            }
        }

        Map<String, Object> model = new HashMap<>();
        model.put("order", order);
        return new ModelAndView(new MyPdfView(), model);
    }

}
