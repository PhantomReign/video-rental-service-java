package com.videorentalservice.services;

import com.querydsl.core.types.Predicate;
import com.videorentalservice.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by Rave on 18.02.2017.
 */
public interface UserService extends JpaService<User> {
    User findByUsername(String username);
    Page<User> findAll(Predicate predicate, Pageable pageable);
}