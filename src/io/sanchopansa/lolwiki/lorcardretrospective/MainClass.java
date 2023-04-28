package io.sanchopansa.lolwiki.lorcardretrospective;

import java.net.MalformedURLException;

public class MainClass {
    public static void main(String[] args) {
        JSONFetcher fetcher = new JSONFetcher("2", "01DE001");
        try {
            fetcher.performConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
