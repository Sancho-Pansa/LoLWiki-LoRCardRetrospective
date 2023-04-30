package io.sanchopansa.lolwiki.lorcardretrospective;

import java.io.BufferedReader;
import java.net.MalformedURLException;

public class MainClass {
    public static void main(String[] args) {
        LoRCardRetrospector retrospector = new LoRCardRetrospector("01DE009");
        retrospector.getCardRetrospection();
    }
}
