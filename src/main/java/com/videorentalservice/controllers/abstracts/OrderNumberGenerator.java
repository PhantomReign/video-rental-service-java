package com.videorentalservice.controllers.abstracts;
import java.security.SecureRandom;
import java.math.BigInteger;

/**
 * Created by Peter on 26.4.2017.
 */

public final class OrderNumberGenerator {
    private SecureRandom random = new SecureRandom();

    public String nextSessionId() {
        return new BigInteger(130, random).toString(32);
    }
}