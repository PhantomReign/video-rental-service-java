package com.videorentalservice.services;

import com.videorentalservice.models.User;

/**
 * Created by Rave on 18.02.2017.
 */
public interface UserService extends JpaService<User> {
    User getByEmail(String email);
    User getByUserName(String userName);
    public String resetPassword(String email);
    public void updatePassword(String email, String token, String password);
    public boolean verifyPasswordResetToken(String email, String token);
}