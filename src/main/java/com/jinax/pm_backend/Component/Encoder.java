package com.jinax.pm_backend.Component;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author : chara
 */
@Component
public class Encoder {
    @Bean
    public PasswordEncoder passwordEncoder() {
        //user BCrypt
        return new BCryptPasswordEncoder();
        // no encryption
//        return new PasswordEncoder() {
//            @Override
//            public String encode(CharSequence charSequence) {
//                return charSequence.toString();
//            }
//
//            @Override
//            public boolean matches(CharSequence charSequence, String s) {
//                return s.equals(charSequence.toString());
//            }
//        };
    }
}
