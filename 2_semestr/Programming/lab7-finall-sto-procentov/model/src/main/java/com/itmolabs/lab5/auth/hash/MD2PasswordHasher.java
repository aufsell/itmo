package com.itmolabs.lab5.auth.hash;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.MessageDigest;
import java.security.Security;
import java.util.Base64;

public class MD2PasswordHasher implements Hasher {

    @Override
    public void init() {
        Security.addProvider(new BouncyCastleProvider());
    }

    @Override
    public boolean check(String password, String hash) {
        return hash(password).equals(hash);
    }

    @Override
    public String hash(String password) {
        try {
            final var md2 = MessageDigest.getInstance("MD2", "BC");

            byte[] passwordBytes = password.getBytes();
            byte[] hash = md2.digest(passwordBytes);

            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
