package com.videorentalservice.controllers;

import com.videorentalservice.controllers.abstracts.AbstractBaseController;
import com.videorentalservice.models.User;
import com.videorentalservice.services.RoleService;
import com.videorentalservice.services.UserService;
import com.videorentalservice.utilities.SecurityUtility;
import com.videorentalservice.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import com.querydsl.core.types.Predicate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;


/**
 * Created by Rave on 05.03.2017.
 */

@Controller
@Secured(SecurityUtility.MANAGE_USERS)
public class UserManageController extends AbstractBaseController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }


    @ModelAttribute("allRoles")
    public List<?> roleList(){
        return roleService.listAll();
    }



    @RequestMapping(value = "/admin/users", method = RequestMethod.GET)
    public String listUsers(Model model,
                            @QuerydslPredicate(root = User.class) Predicate predicate,
                            @PageableDefault(sort = { "id", "userName", "firstName", "lastName" },
                                    value = 8) Pageable pageable,
                            @RequestParam MultiValueMap<String, String> parameters) {

        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequest();
        builder.replaceQueryParam("page");
        model.addAttribute("users", userService.findAll(predicate, pageable));
        model.addAttribute("roles", roleService.listAll());

        return "user/users";
    }

    @RequestMapping("admin/user/show/{id}")
    public String showUser(@PathVariable Integer id, Model model){
        model.addAttribute("user", userService.getById(id));
        return "user/user-show";
    }

    @RequestMapping("admin/user/edit/{id}")
    public String editUser(@PathVariable Integer id, Model model){
        model.addAttribute("user", userService.getById(id));
        model.addAttribute("allRoles", roleService.listAll());
        return "user/user-form-update";
    }

    @RequestMapping("admin/user/new")
    public String newUser(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.listAll());
        return "user/user-form-create";
    }

    @RequestMapping(value = "/admin/user/new", method = RequestMethod.POST)
    public String saveUser(@Valid @ModelAttribute("user") User user,
                           BindingResult result,
                           RedirectAttributes redirectAttributes){

        userValidator.validate(user, result);
        if(result.hasErrors()){
            return "user/user-form-create";
        }
        userService.save(user);
        redirectAttributes.addFlashAttribute("info", "Užívateľ bol úspešne vytvorený.");
        return "redirect:/admin/users";
    }

    @RequestMapping(value = "/admin/user/edit/{id}", method = RequestMethod.POST)
    public String updateUser(@Valid @ModelAttribute("user") User user,
                             BindingResult result,
                             RedirectAttributes redirectAttributes){
        if(result.hasErrors()){
            return "user/user-form-update";
        }
        userService.update(user);
        redirectAttributes.addFlashAttribute("info", "Užívateľ bol úspešne upravený.");
        return "redirect:/admin/users";
    }

    @RequestMapping("admin/user/delete/{id}")
    public String deleteUser(@PathVariable Integer id,
                             RedirectAttributes redirectAttributes){
        userService.delete(id);
        redirectAttributes.addFlashAttribute("info", "Užívateľ bol úspešne vymazaný.");
        return "redirect:/admin/users";
    }
}
