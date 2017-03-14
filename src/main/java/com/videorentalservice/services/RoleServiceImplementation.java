package com.videorentalservice.services;

import com.querydsl.core.types.Predicate;
import com.videorentalservice.models.Role;
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
@Profile("springdatajpa")
public class RoleServiceImplementation implements RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
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
    public Role saveOrUpdate(Role roleObject) {
        return roleRepository.save(roleObject);
    }

    @Override
    public void delete(Integer id) {
        roleRepository.delete(id);
    }
}