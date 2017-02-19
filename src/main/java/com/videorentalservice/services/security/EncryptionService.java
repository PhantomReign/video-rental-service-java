package com.videorentalservice.services.security;

/**
 * Created by Rave on 18.02.2017.
 */

public interface EncryptionService {
    String encryptString(String input);
    boolean checkPassword(String plainPassword, String encryptedPassword);
}