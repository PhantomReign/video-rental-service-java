package com.videorentalservice.services;

import com.querydsl.core.types.Predicate;
import com.videorentalservice.VRSException;
import com.videorentalservice.models.Role;
import com.videorentalservice.models.User;
import com.videorentalservice.repositories.RoleRepository;
import com.videorentalservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Rave on 18.02.2017.
 */

@Service
public class UserServiceImplementation implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<?> listAll() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    @Override
    public User getById(Integer id) {
        return userRepository.findOne(id);
    }

    @Override
    public User getByUserName(String userName) {
        return userRepository.getByUserName(userName);
    }

    @Override
    public User save(User userObject) {
        User userByEmail = getByEmail(userObject.getEmail());
        if(userByEmail != null){
            throw new VRSException("Email "+userObject.getEmail()+" already in use");
        }
        List<Role> persistedRoles = new ArrayList<>();
        List<Role> roles = userObject.getRoles();
        if(roles != null){
            for (Role role : roles) {
                if(role.getId() != null)
                {
                    persistedRoles.add(roleRepository.findOne(role.getId()));
                }
            }
        }
        userObject.setRoles(persistedRoles);

        return userRepository.save(userObject);
    }

    @Override
    public User register(User userObject) {
        User userByEmail = getByEmail(userObject.getEmail());
        if(userByEmail != null){
            throw new VRSException("Email "+userObject.getEmail()+" already in use");
        }
        List<Role> persistedRoles = new ArrayList<>();
        Role role = roleRepository.getByName("ROLE_USER");
        persistedRoles.add(roleRepository.findOne(role.getId()));
        userObject.setRoles(persistedRoles);

        return userRepository.save(userObject);
    }

    @Override
    public User update(User userObject) {
        User persistedUser = getById(userObject.getId());
        if(persistedUser == null){
            throw new VRSException("User "+userObject.getId()+" doesn't exist");
        }

        List<Role> updatedRoles = new ArrayList<>();
        List<Role> roles = userObject.getRoles();
        if(roles != null){
            for (Role role : roles) {
                if(role.getId() != null)
                {
                    updatedRoles.add(roleRepository.findOne(role.getId()));
                }
            }
        }
        persistedUser.setRoles(updatedRoles);
        persistedUser.setFirstName(userObject.getFirstName());
        persistedUser.setLastName(userObject.getLastName());
        persistedUser.setAddress(userObject.getAddress());
        persistedUser.setPhone(userObject.getPhone());
        persistedUser.setPasswordResetToken(userObject.getPasswordResetToken());
        return userRepository.save(persistedUser);
    }



    @Override
    @Transactional
    public void delete(Integer id) {
        userRepository.delete(id);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    @Override
    public Page<User> findAll(Predicate predicate, Pageable pageable) {
        return userRepository.findAll(predicate, pageable);
    }

    @Override
    public String resetPassword(String email) {
        User user = getByEmail(email);
        if(user == null) {
            throw new VRSException("Invalid email address");
        }
        String uuid = UUID.randomUUID().toString();
        user.setPasswordResetToken(uuid);
        update(user);

        return uuid;
    }

    @Override
    public void updatePassword(String email, String token, String password) {
        User user = getByEmail(email);
        if(user == null) {
            throw new VRSException("Invalid email address");
        }
        if(!StringUtils.hasText(token) || !token.equals(user.getPasswordResetToken())) {
            throw new VRSException("Invalid password reset token");
        }
        user.setPassword(password);
        user.setPasswordResetToken(null);
        update(user);

    }

    @Override
    public boolean verifyPasswordResetToken(String email, String token) {
        User user = getByEmail(email);
        if(user == null) {
            throw new VRSException("Invalid email address");
        }

        return !(!StringUtils.hasText(token) || !token.equals(user.getPasswordResetToken()));
    }

}

