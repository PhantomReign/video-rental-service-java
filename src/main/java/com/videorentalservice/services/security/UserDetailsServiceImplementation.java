package com.videorentalservice.services.security;

import com.videorentalservice.models.User;
import com.videorentalservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Rave on 18.02.2017.
 */

@Service("userDetailsService")
@Transactional
public class UserDetailsServiceImplementation implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userName)
            throws UsernameNotFoundException {
        User user = userService.getByEmail(userName);
        if(user == null){
            throw new UsernameNotFoundException("Email "+userName+" not found");
        }
        return new AuthenticatedUser(user);
    }
}