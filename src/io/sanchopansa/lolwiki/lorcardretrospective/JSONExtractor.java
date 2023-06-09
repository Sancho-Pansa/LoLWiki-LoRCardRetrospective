package io.sanchopansa.lolwiki.lorcardretrospective;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JSONExtractor {

    private BufferedReader bReader;
    private String json;
    public JSONExtractor(BufferedReader br) {
        this.bReader = br;
    }

    public JSONExtractor(String json) {
        this.json = json;
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

    private List<String> parseJsonSubArray(JsonElement jElement) {
        List<String> resultList = new ArrayList<>();
        Optional<JsonElement> optionalSubType = Optional.ofNullable(jElement);
        if(optionalSubType.isPresent())
            resultList = optionalSubType.get().getAsJsonArray()
                    .asList()
                    .stream()
                    .map(j -> j.getAsString().charAt(0) + j.getAsString().substring(1).toLowerCase())
                    .toList();
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
        return null;
    }

    public LoRCard getLoRCardByCode(String cardCode) {
        LoRCard card = null;
        JsonArray jsonArray = JsonParser.parseString(json).getAsJsonArray();
        Optional<JsonElement> optionalElement = jsonArray
                .asList()
                .stream()
                .filter(j -> {
                    Map<String, JsonElement> cardMap = j.getAsJsonObject().asMap();
                    return cardMap.get("cardCode").getAsString().equals(cardCode);
                })
                .findFirst();
        if(optionalElement.isPresent()) {
            JsonElement jElement = optionalElement.get();
            Map<String, JsonElement> cardMap = jElement.getAsJsonObject().asMap();
            LoRCard.cardBuilder cardBuilder = new LoRCard.cardBuilder();

            cardBuilder.cardCode(cardMap.get("cardCode").getAsString());
            cardBuilder.cardName(cardMap.get("name").getAsString());
            cardBuilder.type(cardMap.get("type").getAsString());
            cardBuilder.subType(parseJsonSubArray(cardMap.get("subtypes")));
            cardBuilder.rarity(cardMap.get("rarity").getAsString());
            cardBuilder.collectible(cardMap.get("collectible").getAsBoolean());

            cardBuilder.keywords(parseJsonSubArray(cardMap.get("keywords")));
            cardBuilder.cost(cardMap.get("cost").getAsInt());
            cardBuilder.health(cardMap.get("health").getAsInt());
            cardBuilder.attack(cardMap.get("attack").getAsInt());

            cardBuilder.desc(cardMap.get("descriptionRaw").getAsString());
            cardBuilder.lvlDesc(cardMap.get("descriptionRaw").getAsString());
            cardBuilder.flavour(cardMap.get("flavorText").getAsString());
            cardBuilder.artists(cardMap.get("artistName").getAsString());
            card = cardBuilder.build();
        }
        return card;
    }
}
