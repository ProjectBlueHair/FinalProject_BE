package com.bluehair.hanghaefinalproject.common.service;

import javax.sound.sampled.AudioFormat;
import java.util.regex.Pattern;

public class Validator {
    public static boolean isValidEmail(String email){
        final String REGEX = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";

        return Pattern.matches(REGEX, email);
    }
    public static boolean isValidPassword(String password){
        final int MAX = 15;
        final int MIN = 8;

        final String REGEX = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{" + MIN + "," + MAX + "}$";

        return Pattern.matches(REGEX, password);
    }

    public static boolean isValidAudioFormat(AudioFormat format){
        if (format.getChannels() != 2 ) {
            return false;
        }
        return format.getSampleSizeInBits() == 16 || format.getSampleSizeInBits() == 8;
    }
}
