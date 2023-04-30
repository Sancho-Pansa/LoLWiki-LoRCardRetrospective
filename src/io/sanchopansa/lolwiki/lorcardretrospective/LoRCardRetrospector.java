package io.sanchopansa.lolwiki.lorcardretrospective;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class LoRCardRetrospector {
    private final String cardCode;
    private final String PATCHES_LIST = "resources\\patches.txt";

    public LoRCardRetrospector(String cardCode) {
        this.cardCode = cardCode;
    }

    public void getCardRetrospection() {
        List<String> patchesList = getPatchesList();
        ArrayList<LoRCard> cardHistory = new ArrayList<>();
        for(String x: patchesList) {
            JSONFetcher fetcher = new JSONFetcher(x, getSetFromCode());
            try {
                BufferedReader bReader = fetcher.performConnection();
                JSONExtractor jsonExtractor = new JSONExtractor(bReader);
                cardHistory.add(jsonExtractor.getCardData(cardCode));
                bReader.close();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        printCardHistory(cardHistory);
    }

    private void printCardHistory(List<LoRCard> cardHistory) {
        BiConsumer<String, String> printDifference = (a, b) -> System.out.printf("%s → %s%n", a, b);
        for(int i = 1; i < cardHistory.size(); i++) {
            LoRCard a = cardHistory.get(i - 1);
            LoRCard b = cardHistory.get(i);
            if(!a.deepEquals(b)) {
                if(!a.getCardName().equals(b.getCardName()))
                    printDifference.accept(a.getCardName(), b.getCardName());
                if(!a.getType().equals(b.getType()))
                    printDifference.accept(a.getCardName(), b.getCardName());
                if(!a.getRarity().equals(b.getRarity()))
                    printDifference.accept(a.getCardName(), b.getCardName());
                if(!a.getDesc().equals(b.getDesc()))
                    printDifference.accept(a.getDesc(), b.getDesc());
                if(!a.getLvlDesc().equals(b.getLvlDesc()))
                    printDifference.accept(a.getLvlDesc(), b.getLvlDesc());
                if(!a.getFlavour().equals(b.getFlavour()))
                    printDifference.accept(a.getFlavour(), b.getFlavour());
                if(!a.getArtists().equals(b.getArtists()))
                    printDifference.accept(a.getArtists(), b.getArtists());
                if(a.getCost() != b.getCost())
                    System.out.printf("%d → %d%n", a.getCost(), b.getCost());
                if(a.getPower() != b.getPower())
                    System.out.printf("%d → %d%n", a.getPower(), b.getPower());
                if(a.getHealth() != b.getHealth())
                    System.out.printf("%d → %d%n", a.getHealth(), b.getHealth());
                if(a.isCollectible() != b.isCollectible())
                    System.out.printf("%s → %s%n", a.isCollectible(), b.isCollectible());
                if(a.getKeywords() != b.getKeywords())
                    System.out.printf("%s → %s%n", a.getKeywords(), b.getKeywords());
                if(a.getSubType() != b.getSubType())
                    System.out.printf("%s → %s%n", a.getSubType(), b.getSubType());
                if(a.getFormats() != b.getFormats())
                    System.out.printf("%s → %s%n", a.getFormats(), b.getFormats());
            }
        }
    }

    public List<String> getPatchesList() {
        Path patchesPath = Paths.get(PATCHES_LIST);
        List<String> patches = new ArrayList<>();
        try(BufferedReader bReader = Files.newBufferedReader(patchesPath)) {
            patches = bReader.lines().map((String patch) -> patch.replace(".", "_")).toList();
        } catch(IOException e) {
            e.printStackTrace();
        }
        if(patches.isEmpty()) {
            System.err.println("Patches List is empty");
        }
        return patches;
    }

    private String getSetFromCode() {
        return cardCode.substring(1, 2);
    }
}
