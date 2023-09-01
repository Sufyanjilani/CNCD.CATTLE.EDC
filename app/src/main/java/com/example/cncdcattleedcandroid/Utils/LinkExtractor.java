package com.example.cncdcattleedcandroid.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkExtractor {

    public static List<String> extractLinks(String text) {
        List<String> links = new ArrayList<>();

        // Define the regex pattern for matching URLs
        String regex = "(http?://\\S+|www\\.\\S+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        // Find and add all matched links to the list
        while (matcher.find()) {
            String link = matcher.group();
            links.add(link);
        }

        return links;
    }
}
