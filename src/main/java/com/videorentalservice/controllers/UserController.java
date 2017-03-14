package com.videorentalservice.controllers;

import com.videorentalservice.models.User;
import com.videorentalservice.services.RoleService;
import com.videorentalservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

import com.querydsl.core.types.Predicate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


/**
 * Created by Rave on 05.03.2017.
 */

@Controller
public class UserController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public void setDiscService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }



    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String list(Model model,
                       @QuerydslPredicate(root = User.class) Predicate predicate,
                       @PageableDefault(sort = { "id", "username" }, value = 8) Pageable pageable,
                       @RequestParam MultiValueMap<String, String> parameters) {

        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequest();
        builder.replaceQueryParam("page");
        model.addAttribute("users", userService.findAll(predicate, pageable));
        model.addAttribute("roles", roleService.listAll());

        return "users";
    }

    @RequestMapping("user/show/{id}")
    public String showUser(@PathVariable Integer id, Model model){
        model.addAttribute("user", userService.getById(id));
        return "user-show";
    }

    @RequestMapping("user/edit/{id}")
    public String editUser(@PathVariable Integer id, Model model){
        model.addAttribute("user", userService.getById(id));
        model.addAttribute("allRoles", roleService.listAll());
        return "user-form";
    }

    @RequestMapping("user/new")
    public String newUser(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.listAll());
        return "user-form";
    }

    @RequestMapping(value = "user", method = RequestMethod.POST)
    public String saveUser(User user){
        userService.saveOrUpdate(user);
        return "redirect:/user/" + user.getId();
    }

    @RequestMapping("user/delete/{id}")
    public String deleteUser(@PathVariable Integer id){
        userService.delete(id);
        return "redirect:/users";
    }
}
