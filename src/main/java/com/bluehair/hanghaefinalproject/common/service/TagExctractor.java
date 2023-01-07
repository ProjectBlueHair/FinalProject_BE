package com.bluehair.hanghaefinalproject.common.service;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TagExctractor {
    public List<String> extractAtTags(String contents) {
        List<String> result = new ArrayList<>();
        String[] splitted = contents.split(" ");
        for (String s : splitted) {
            if(s.charAt(0) == '@'){
                result.add(s.substring(1));
            }
        }
        return result;
    }

    public List<String> extractHashTags(String contents) {
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
