package dev.mednikov.expensetracking.authentication.utils;

import org.springframework.util.DigestUtils;

import java.util.UUID;

public class AuthTokenUtils {

    public static String generateNewToken (){
        return DigestUtils.md5DigestAsHex(UUID.randomUUID().toString().getBytes());
    }

}
