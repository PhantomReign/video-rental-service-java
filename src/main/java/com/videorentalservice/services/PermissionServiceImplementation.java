package com.videorentalservice.services;

import com.querydsl.core.types.Predicate;
import com.videorentalservice.models.Permission;
import com.videorentalservice.repositories.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rave on 21.03.2017.
 */
@Service
public class PermissionServiceImplementation implements PermissionService {

    private PermissionRepository permissionRepository;

    @Autowired
    public void setRoleRepository(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public List<?> listAll() {
        List<Permission> permissions = new ArrayList<>();
        permissionRepository.findAll().forEach(permissions::add);
        return permissions;
    }

    @Override
    public Page<Permission> findAll(Predicate predicate, Pageable pageable) {
        return permissionRepository.findAll(predicate, pageable);
    }

    @Override
    public Permission getById(Integer id) {
        return permissionRepository.findOne(id);
    }

    @Override
    public Permission save(Permission roleObject) {
        return permissionRepository.save(roleObject);
    }

    @Override
    public Permission update(Permission roleObject) {
        return permissionRepository.save(roleObject);
    }

    @Override
    public void delete(Integer id) {
        permissionRepository.delete(id);
    }
}
