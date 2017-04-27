package com.videorentalservice.controllers;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.videorentalservice.controllers.abstracts.AbstractBaseController;
import com.videorentalservice.models.Order;
import com.videorentalservice.models.QOrder;
import com.videorentalservice.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * Created by Rave on 27.04.2017.
 */
@Controller
public class IndexAdminController extends AbstractBaseController {

    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }


    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String listPermissions(Model model,
                                  @QuerydslPredicate(root = Order.class) Predicate predicate,
                                  @PageableDefault(sort = { "id", "orderNumber", "price", "fromDate", "toDate", "status" }, value = 10) Pageable pageable,
                                  @RequestParam MultiValueMap<String, String> parameters) {

        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequest();
        builder.replaceQueryParam("page");

        QOrder qOrder = QOrder.order;
        BooleanExpression isNew = qOrder.status.eq("Nová");
        model.addAttribute("newOrders", orderService.findAll(isNew, pageable));

        return "adminIndex";
    }
}
