package com.bluehair.hanghaefinalproject.common.service;

import java.util.ArrayList;
import java.util.List;

public class TagExctractor {
    public static List<String> extractAtTags(String contents) {
        List<String> result = new ArrayList<>();
        String[] splitted = contents.split(" ");
        for (String s : splitted) {
            if(s.charAt(0) == '@'){
                result.add(s.substring(1));
            }
        }
        return result;
    }

    public static List<String> extractHashTags(String contents) {
        List<String> result = new ArrayList<>();
        String[] splitted = contents.split(" ");
        for (String s : splitted) {
            if(s.charAt(0) == '#'){
                result.add(s.substring(1));
            }
        }
        return result;
    }
}
