package com.kabir.kabirbackend.config.security.encryption;

import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return myEncrypt(rawPassword != null ? rawPassword.toString() : "");
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return myEncrypt(rawPassword != null ? rawPassword.toString() : "").equals(encodedPassword);
    }

    private String myEncrypt(String input) {
        return Encryption.strEncrypt(input, 7);
    }
}
