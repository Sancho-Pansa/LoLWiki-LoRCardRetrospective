package io.sanchopansa.lolwiki.lorcardretrospective;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JSONExtractor {

    private BufferedReader bReader;
    public JSONExtractor(BufferedReader br) {
        this.bReader = br;
    }

    private LoRCard parseJsonCardArray(JsonReader jReader) throws IOException {
        LoRCard.cardBuilder cardBuilder = new LoRCard.cardBuilder();

        jReader.beginObject();
        while (jReader.hasNext()) {
            String name = jReader.nextName();
            switch (name) {
                case "cardCode" -> cardBuilder.cardCode(jReader.nextString());
                case "name" -> cardBuilder.cardName(jReader.nextString());
                case "type" -> cardBuilder.type(jReader.nextString());
                case "subtypes" -> cardBuilder.subType(parseJsonSubArray(jReader));
                case "rarityRef" -> cardBuilder.rarity(jReader.nextString());
                case "collectible" -> cardBuilder.collectible(jReader.nextBoolean());
                case "keywords" -> cardBuilder.keywords(parseJsonSubArray(jReader));
                case "cost" -> cardBuilder.cost(jReader.nextInt());
                case "attack" -> cardBuilder.attack(jReader.nextInt());
                case "health" -> cardBuilder.health(jReader.nextInt());
                case "descriptionRaw" -> cardBuilder.desc(jReader.nextString());
                case "levelupDescriptionRaw" -> cardBuilder.lvlDesc(jReader.nextString());
                case "flavorText" -> cardBuilder.flavour(jReader.nextString());
                case "artistName" -> cardBuilder.artists(jReader.nextString());
                default -> jReader.skipValue();
            }
        }
        jReader.endObject();
        return cardBuilder.build();
    }

    private List<String> parseJsonSubArray(JsonReader jReader) throws IOException {
        ArrayList<String> resultList = new ArrayList<>();
        jReader.beginArray();
        while(jReader.hasNext()) {
            resultList.add(jReader.nextString());
        }
        jReader.endArray();
        return resultList;
    }

    public LoRCard getCardData(String cardCode) throws IOException {
        Gson gson = new Gson();
        LoRCard card = null;
        JsonReader jsonReader = gson.newJsonReader(bReader);
        jsonReader.setLenient(true);
        jsonReader.beginArray();
        while(jsonReader.hasNext()) {
            card = parseJsonCardArray(jsonReader);
            if(card.getCardCode().equals(cardCode)) {
                jsonReader.skipValue();
                return card;
            }
        }
        jsonReader.endArray();
        return card;
    }
}
