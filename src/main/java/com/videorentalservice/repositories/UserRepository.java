package com.videorentalservice.repositories;

import com.videorentalservice.models.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Rave on 18.02.2017.
 */
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);
}
