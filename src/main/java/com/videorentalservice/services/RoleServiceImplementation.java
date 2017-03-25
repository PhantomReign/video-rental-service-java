package com.videorentalservice.services;

import com.querydsl.core.types.Predicate;
import com.videorentalservice.VRSException;
import com.videorentalservice.models.Permission;
import com.videorentalservice.models.Role;
import com.videorentalservice.repositories.PermissionRepository;
import com.videorentalservice.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rave on 18.02.2017.
 */

@Service
public class RoleServiceImplementation implements RoleService {

    private RoleRepository roleRepository;
    private PermissionRepository permissionRepository;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setPermissionRepository(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public List<?> listAll() {
        List<Role> roles = new ArrayList<>();
        roleRepository.findAll().forEach(roles::add);
        return roles;
    }

    @Override
    public Page<Role> findAll(Predicate predicate, Pageable pageable) {
        return roleRepository.findAll(predicate, pageable);
    }

    @Override
    public Role getById(Integer id) {
        return roleRepository.findOne(id);
    }

    @Override
    public Role getByName(String name) {
        return roleRepository.getByName(name);
    }

    @Override
    public Role save(Role roleObject) {
        Role roleByName = getByName(roleObject.getName());
        if(roleByName != null){
            throw new VRSException("Role "+roleObject.getName()+" already exist");
        }
        List<Permission> persistedPermissions = new ArrayList<>();
        List<Permission> permissions = roleObject.getPermissions();
        if(permissions != null){
            for (Permission permission : permissions) {
                if(permission.getId() != null)
                {
                    persistedPermissions.add(permissionRepository.findOne(permission.getId()));
                }
            }
        }

        roleObject.setPermissions(persistedPermissions);
        return roleRepository.save(roleObject);
    }

    @Override
    public Role update(Role roleObject) {
        Role persistedRole = getById(roleObject.getId());
        if(persistedRole == null){
            throw new VRSException("Role "+roleObject.getId()+" doesn't exist");
        }
        List<Permission> updatedPermissions = new ArrayList<>();
        List<Permission> permissions = roleObject.getPermissions();
        if(permissions != null){
            for (Permission permission : permissions) {
                if(permission.getId() != null)
                {
                    updatedPermissions.add(permissionRepository.findOne(permission.getId()));
                }
            }
        }
        persistedRole.setPermissions(updatedPermissions);
        return roleRepository.save(persistedRole);
    }

    @Override
    public void delete(Integer id) {
        roleRepository.delete(id);
    }
}