package com.bluehair.hanghaefinalproject.common.service;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class Validator {
    public boolean isValidEmail(String email){
        final String REGEX = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";

        return Pattern.matches(REGEX, email);
    }
    public boolean isValidPassword(String password){
        final int MAX = 15;
        final int MIN = 8;

        final String REGEX = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{" + MIN + "," + MAX + "}$";

        return Pattern.matches(REGEX, password);
    }
}
