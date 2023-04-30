package io.sanchopansa.lolwiki.lorcardretrospective;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JSONFetcher {
    private String patchNumber = "latest";
    private String setCode;

    /* https://dd.b.pvp.net/<patch number>/set<set code>/ru_ru/data/set<set code>-ru_ru.json */
    private final String URL_PREFIX = "https://dd.b.pvp.net/";

    public JSONFetcher(String setCode) {
        this.setCode = setCode;
    }

    public JSONFetcher(String patchNumber, String setCode) {
        this.patchNumber = patchNumber;
        this.setCode = setCode;
    }

    public BufferedReader performConnection() throws MalformedURLException {
        URL url = new URL(String.format(
                URL_PREFIX + "%s/set%s/ru_ru/data/set%s-ru_ru.json",
                patchNumber,
                setCode,
                setCode
                ));
        System.out.println(url);
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0");

            int responseCode = urlConnection.getResponseCode();
            System.out.println("HTTP :: " + responseCode);
            if(responseCode == HttpURLConnection.HTTP_OK) {
                InputStream is = urlConnection.getInputStream();
                BufferedReader bReader = new BufferedReader(new InputStreamReader(is));
                return bReader;
            } else {
                throw new IOException("HTTP request return non-200 response");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
