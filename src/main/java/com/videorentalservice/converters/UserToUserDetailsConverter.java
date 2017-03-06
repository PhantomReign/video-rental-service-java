package com.videorentalservice.converters;

import com.videorentalservice.models.User;
import com.videorentalservice.services.security.UserDetailsImplementation;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Rave on 18.02.2017.
 */

@Component
public class UserToUserDetailsConverter implements Converter<User, UserDetails> {
    @Override
    public UserDetails convert(User user) {
        UserDetailsImplementation userDetailsImplementation = new UserDetailsImplementation();

        if (user != null) {
            userDetailsImplementation.setUsername(user.getUsername());
            userDetailsImplementation.setPassword(user.getEncryptedPassword());
            userDetailsImplementation.setEnabled(user.getEnabled());
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRoleName())));
            userDetailsImplementation.setAuthorities(authorities);
        }

        return userDetailsImplementation;
    }
}