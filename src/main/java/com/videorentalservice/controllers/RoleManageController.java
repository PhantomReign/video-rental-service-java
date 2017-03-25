package com.videorentalservice.controllers;

import com.querydsl.core.types.Predicate;
import com.videorentalservice.controllers.abstracts.AbstractBaseController;
import com.videorentalservice.models.Role;
import com.videorentalservice.services.PermissionService;
import com.videorentalservice.services.RoleService;
import com.videorentalservice.utilities.SecurityUtility;
import com.videorentalservice.validators.RoleValidator;
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
import java.util.List;

/**
 * Created by Rave on 23.03.2017.
 */

@Controller
@Secured(SecurityUtility.MANAGE_ROLES)
public class RoleManageController extends AbstractBaseController{

    private RoleService roleService;
    private PermissionService permissionService;

    @Autowired
    private RoleValidator roleValidator;

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }


    @ModelAttribute("allPermissions")
    public List<?> permissionsList(){
        return permissionService.listAll();
    }


    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public String listRoles(Model model,
                       @QuerydslPredicate(root = Role.class) Predicate predicate,
                       @PageableDefault(sort = { "id", "name" }, value = 10) Pageable pageable,
                       @RequestParam MultiValueMap<String, String> parameters) {

        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequest();
        builder.replaceQueryParam("page");

        model.addAttribute("roles", roleService.findAll(predicate, pageable));
        model.addAttribute("permissions", permissionService.listAll());

        return "role/roles";
    }

    @RequestMapping("role/show/{id}")
    public String showRole(@PathVariable Integer id, Model model){
        model.addAttribute("role", roleService.getById(id));
        return "role/role-show";
    }

    @RequestMapping("role/edit/{id}")
    public String editRole(@PathVariable Integer id, Model model){
        model.addAttribute("role", roleService.getById(id));
        return "role/role-form-update";
    }

    @RequestMapping("role/new")
    public String newRole(Model model){
        model.addAttribute("role", new Role());
        return "role/role-form-create";
    }

    @RequestMapping(value = "/role/new", method = RequestMethod.POST)
    public String saveRole(@Valid @ModelAttribute("role") Role role, BindingResult result,
                           RedirectAttributes redirectAttributes){

        roleValidator.validate(role, result);
        if(result.hasErrors()){
            return "role/role-form-create";
        }
        roleService.save(role);
        redirectAttributes.addFlashAttribute("info", "Rola bola úspešne vytvorená.");
        return "redirect:/roles";
    }

    @RequestMapping(value="/role/edit/{id}", method=RequestMethod.POST)
    public String updateRole(@ModelAttribute("role") Role role,
                             RedirectAttributes redirectAttributes){

        roleService.update(role);
        redirectAttributes.addFlashAttribute("info", "Rola bola úspešne upravená.");
        return "redirect:/roles";
    }

    @RequestMapping("role/delete/{id}")
    public String deleteRole(@PathVariable Integer id,
                             RedirectAttributes redirectAttributes){
        roleService.delete(id);
        redirectAttributes.addFlashAttribute("info", "Rola bola úspešne vymazaná.");
        return "redirect:/roles";
    }

}
