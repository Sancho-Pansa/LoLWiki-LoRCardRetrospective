package io.sanchopansa.lolwiki.lorcardretrospective;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class LoRCardRetrospector {
    private final String cardCode;
    private final String PATCHES_LIST = "resources\\patches.txt";

    private TreeMap<String, LoRCard> cardHistory = new TreeMap<>();

    public LoRCardRetrospector(String cardCode) {
        this.cardCode = cardCode;
    }

    public void getCardRetrospection() {
        List<String> patchesList = getPatchesList();
        for(String x: patchesList) {
            JSONFetcher fetcher = new JSONFetcher(x, getSetFromCode());
            try {
                BufferedReader bReader = fetcher.performConnection();
                JSONExtractor jsonExtractor = new JSONExtractor(bReader);
                this.cardHistory.put(x, jsonExtractor.getCardData(cardCode));
                bReader.close();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        printCardHistory();
    }

    private void printCardHistory() {
        List<String> patches = cardHistory
                .keySet()
                .stream()
                .sorted((a, b) -> {
                    String[] aSplit = a.split("_");
                    String[] bSplit = b.split("_");
                    for(int i = 0; i < aSplit.length; i++) {
                        if((int) Integer.parseInt(aSplit[i]) != Integer.parseInt(bSplit[i]))
                            return Integer.compare(Integer.parseInt(aSplit[i]), Integer.parseInt(bSplit[i]));
                    }
                    return 0;
                }).toList();
        for(int i = 1; i < patches.size(); i++) {
            LoRCard a = cardHistory.get(patches.get(i - 1));
            LoRCard b = cardHistory.get(patches.get(i));
            if(!a.deepEquals(b)) {
                System.out.println(patches.get(i));
                if(!a.getCardName().equals(b.getCardName()))
                    System.out.printf("Name: %s → %s%n, ", a.getCardName(), b.getCardName());
                if(!a.getType().equals(b.getType()))
                    System.out.printf("Type: %s → %s%n, ", a.getCardName(), b.getCardName());
                if(!a.getRarity().equals(b.getRarity()))
                    System.out.printf("Rarity: %s → %s%n, ", a.getCardName(), b.getCardName());
                if(!a.getDesc().equals(b.getDesc()))
                    System.out.printf("Desc: %s → %s%n, ", a.getDesc(), b.getDesc());
                if(!a.getLvlDesc().equals(b.getLvlDesc()))
                    System.out.printf("Level-up Desc: %s → %s%n, ", a.getLvlDesc(), b.getLvlDesc());
                if(!a.getFlavour().equals(b.getFlavour()))
                    System.out.printf("Flavour: %s → %s%n, ", a.getFlavour(), b.getFlavour());
                if(!a.getArtists().equals(b.getArtists()))
                    System.out.printf("Artists: %s → %s%n, ", a.getArtists(), b.getArtists());
                if(a.getCost() != b.getCost())
                    System.out.printf("Cost: %d → %d%n", a.getCost(), b.getCost());
                if(a.getAttack() != b.getAttack())
                    System.out.printf("Power: %d → %d%n", a.getAttack(), b.getAttack());
                if(a.getHealth() != b.getHealth())
                    System.out.printf("Health: %d → %d%n", a.getHealth(), b.getHealth());
                if(a.isCollectible() != b.isCollectible())
                    System.out.printf("Collectible: %s → %s%n", a.isCollectible(), b.isCollectible());
                if(!a.getKeywords().equals(b.getKeywords()))
                    System.out.printf("Keywords: %s → %s%n", a.getKeywords(), b.getKeywords());
                if(!a.getSubType().equals(b.getSubType()))
                    System.out.printf("Subtype: %s → %s%n", a.getSubType(), b.getSubType());
                if(!a.getFormats().equals(b.getFormats()))
                    System.out.printf("Formats: %s → %s%n", a.getFormats(), b.getFormats());
            }
        }
    }

    private List<String> getPatchesList() {
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
