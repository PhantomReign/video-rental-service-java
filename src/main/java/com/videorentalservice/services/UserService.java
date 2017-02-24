package com.videorentalservice.services;

import com.videorentalservice.models.User;

/**
 * Created by Rave on 18.02.2017.
 */
public interface UserService extends CRUDService<User> {
    User findByUsername(String username);
}