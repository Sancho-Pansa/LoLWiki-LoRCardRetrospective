package io.sanchopansa.lolwiki.lorcardretrospective;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

// TODO: Распараллелить парсинг JSON
public class LoRCardRetrospector {
    private final String cardCode;
    private final String PATCHES_LIST = "resources\\patches.txt";

    private TreeMap<String, LoRCard> cardHistory = new TreeMap<>();
    private List<String> patchesList;

    public LoRCardRetrospector(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getCardRetrospection() {
        patchesList = getPatchesList();
        fetchLoRData();
        return printCardHistory();
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

    private void fetchLoRData() {
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
    }

    private String printCardHistory() {
        Comparator<String> patchComparator = (a, b) -> {
            String[] aSplit = a.split("_");
            String[] bSplit = b.split("_");
            for(int i = 0; i < aSplit.length; i++) {
                if(Integer.parseInt(aSplit[i]) != Integer.parseInt(bSplit[i]))
                    return Integer.compare(Integer.parseInt(aSplit[i]), Integer.parseInt(bSplit[i]));
            }
            return 0;
        };

        List<String> patches = cardHistory
                .keySet()
                .stream()
                .sorted(patchComparator)
                .toList();

        StringBuilder sBuilder = new StringBuilder();

        for(int i = 1; i < patches.size(); i++) {
            LoRCard a = cardHistory.get(patches.get(i - 1));
            LoRCard b = cardHistory.get(patches.get(i));
            if(!a.deepEquals(b)) {
                sBuilder.append(String.format(patches.get(i)));
                if(!a.getCardName().equals(b.getCardName()))
                    sBuilder.append(String.format("Name: %s → %s%n, ", a.getCardName(), b.getCardName()));
                if(!a.getType().equals(b.getType()))
                    sBuilder.append(String.format("Type: %s → %s%n, ", a.getCardName(), b.getCardName()));
                if(!a.getRarity().equals(b.getRarity()))
                    sBuilder.append(String.format("Rarity: %s → %s%n, ", a.getCardName(), b.getCardName()));
                if(!a.getDesc().equals(b.getDesc()))
                    sBuilder.append(String.format("Desc: %s → %s%n, ", a.getDesc(), b.getDesc()));
                if(!a.getLvlDesc().equals(b.getLvlDesc()))
                    sBuilder.append(String.format("Level-up Desc: %s → %s%n, ", a.getLvlDesc(), b.getLvlDesc()));
                if(!a.getFlavour().equals(b.getFlavour()))
                    sBuilder.append(String.format("Flavour: %s → %s%n, ", a.getFlavour(), b.getFlavour()));
                if(!a.getArtists().equals(b.getArtists()))
                    sBuilder.append(String.format("Artists: %s → %s%n, ", a.getArtists(), b.getArtists()));
                if(a.getCost() != b.getCost())
                    sBuilder.append(String.format("Cost: %d → %d%n", a.getCost(), b.getCost()));
                if(a.getAttack() != b.getAttack())
                    sBuilder.append(String.format("Power: %d → %d%n", a.getAttack(), b.getAttack()));
                if(a.getHealth() != b.getHealth())
                    sBuilder.append(String.format("Health: %d → %d%n", a.getHealth(), b.getHealth()));
                if(a.isCollectible() != b.isCollectible())
                    sBuilder.append(String.format("Collectible: %s → %s%n", a.isCollectible(), b.isCollectible()));
                if(!a.getKeywords().equals(b.getKeywords()))
                    sBuilder.append(String.format("Keywords: %s → %s%n", a.getKeywords(), b.getKeywords()));
                if(!a.getSubType().equals(b.getSubType()))
                    sBuilder.append(String.format("Subtype: %s → %s%n", a.getSubType(), b.getSubType()));
                if(!a.getFormats().equals(b.getFormats()))
                    sBuilder.append(String.format("Formats: %s → %s%n", a.getFormats(), b.getFormats()));
            }
        }
        return sBuilder.toString();
    }

    private String getSetFromCode() {
        return cardCode.substring(1, 2);
    }
}
