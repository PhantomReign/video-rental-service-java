package com.videorentalservice.services;

import com.querydsl.core.types.Predicate;
import com.videorentalservice.models.User;
import com.videorentalservice.repositories.UserRepository;
import com.videorentalservice.services.security.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rave on 18.02.2017.
 */

@Service
@Profile("springdatajpa")
public class UserServiceImplementation implements UserService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private EncryptionService encryptionService;

    @Autowired
    public void setEncryptionService(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }


    @Override
    public List<?> listAll() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    @Override
    public Page<User> listAllByPage(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User getById(Integer id) {
        return userRepository.findOne(id);
    }

    @Override
    public User saveOrUpdate(User userObject) {
        if(userObject.getPassword() != null){
            userObject.setEncryptedPassword(encryptionService.encryptString(userObject.getPassword()));
        }
        return userRepository.save(userObject);
    }
    @Override
    @Transactional
    public void delete(Integer id) {
        userRepository.delete(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Page<User> findAll(Predicate predicate, Pageable pageable) {
        return userRepository.findAll(predicate, pageable);
    }
}