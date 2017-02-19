package com.videorentalservice.services.security;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Rave on 18.02.2017.
 */

@Service
public class EncryptionServiceImplementation implements EncryptionService {

    private StrongPasswordEncryptor encryptor;

    @Autowired
    public void setStrongEncryptor(StrongPasswordEncryptor encryptor) {
        this.encryptor = encryptor;
    }

    public String encryptString(String input) {

        return encryptor.encryptPassword(input);
    }

    public boolean checkPassword(String plainPassword, String encryptedPassword) {
        return encryptor.checkPassword(plainPassword, encryptedPassword);
    }
}