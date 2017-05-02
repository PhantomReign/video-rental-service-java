package com.videorentalservice.controllers;

import com.querydsl.core.types.Predicate;
import com.videorentalservice.controllers.abstracts.AbstractBaseController;
import com.videorentalservice.models.Order;
import com.videorentalservice.models.abstracts.OrderStates;
import com.videorentalservice.services.OrderService;
import com.videorentalservice.utilities.SecurityUtility;
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
import java.util.ArrayList;

/**
 * Created by Rave on 27.04.2017.
 */

@Controller
@Secured(SecurityUtility.MANAGE_ORDERS)
public class OrderManageController extends AbstractBaseController {

    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(value = "/admin/orders", method = RequestMethod.GET)
    public String listOrders(Model model,
                                  @QuerydslPredicate(root = Order.class) Predicate predicate,
                                  @PageableDefault(sort = { "id", "orderNumber", "price", "fromDate", "toDate", "status" }, value = 10) Pageable pageable,
                                  @RequestParam MultiValueMap<String, String> parameters) {

        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequest();
        builder.replaceQueryParam("page");

        model.addAttribute("orders", orderService.findAll(predicate, pageable));

        return "order/orders-admin";
    }

    @RequestMapping("admin/order/show/{id}")
    public String showOrderAdmin(@PathVariable Integer id, Model model){
        model.addAttribute("order", orderService.getById(id));
        return "order/order-show-admin";
    }

    @ModelAttribute("orderStatus")
    public ArrayList<String> statusList(){
        ArrayList<String> status = new ArrayList<>();
        status.add(OrderStates.RECEIVED);
        status.add(OrderStates.READY);
        status.add(OrderStates.BORROWED);
        status.add(OrderStates.RETURNED);
        status.add(OrderStates.CANCELED);
        return status;
    }

    @RequestMapping("admin/order/edit/{id}")
    public String editOrder(@PathVariable Integer id, Model model){
        model.addAttribute("order", orderService.getById(id));
        model.addAttribute("orderStatus", statusList());
        return "order/order-form-update";
    }

    @RequestMapping(value = "/admin/order/edit/{id}", method = RequestMethod.POST)
    public String updateOrder(@Valid @ModelAttribute("order") Order order,
                             BindingResult result,
                             RedirectAttributes redirectAttributes){
        if(result.hasErrors()){
            return "order/order-form-update";
        }
        orderService.update(order);
        redirectAttributes.addFlashAttribute("info", "Objednávka bola úspešne upravená.");
        return "redirect:/admin/orders";
    }
}
