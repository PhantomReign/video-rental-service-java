package com.videorentalservice.controllers;

import com.querydsl.core.types.Predicate;
import com.videorentalservice.controllers.abstracts.AbstractBaseController;
import com.videorentalservice.models.Permission;
import com.videorentalservice.services.PermissionService;
import com.videorentalservice.utilities.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

/**
 * Created by Rave on 22.03.2017.
 */
@Controller
@Secured(SecurityUtility.MANAGE_PERMISSIONS)
public class PermissionManageController extends AbstractBaseController {

    private PermissionService permissionService;

    @Autowired
    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @RequestMapping(value = "/admin/permissions", method = RequestMethod.GET)
    public String listPermissions(Model model,
                       @QuerydslPredicate(root = Permission.class) Predicate predicate,
                       @PageableDefault(sort = { "id", "name" }, value = 10) Pageable pageable,
                       @RequestParam MultiValueMap<String, String> parameters) {

        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequest();
        builder.replaceQueryParam("page");

        model.addAttribute("permissions", permissionService.findAll(predicate, pageable));

        return "permission/permissions";
    }
}
