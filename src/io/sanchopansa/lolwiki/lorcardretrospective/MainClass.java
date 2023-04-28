package io.sanchopansa.lolwiki.lorcardretrospective;

import java.io.BufferedReader;
import java.net.MalformedURLException;

public class MainClass {
    public static void main(String[] args) {
        JSONFetcher fetcher = new JSONFetcher("2", "02DE001");
        try {
            BufferedReader bReader = fetcher.performConnection();
            JSONAnalyzer jAnalyzer = new JSONAnalyzer(bReader);
            jAnalyzer.getCardRetrospective("01DE001");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
