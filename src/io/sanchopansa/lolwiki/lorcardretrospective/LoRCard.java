package io.sanchopansa.lolwiki.lorcardretrospective;

import java.util.List;

public class LoRCard {
    private String cardCode;
    private String type;
    private List<String> subType;
    private String rarity;
    private boolean collectible;

    private List<String> keywords;
    private int cost;
    private int power;
    private int health;

    private String desc;
    private String lvlDesc;
    private List<String> formats;

    private String flavour;
    private List<String> artists;

    private List<String> regions;

    public LoRCard(String cardCode,
                   String type,
                   List<String> subType,
                   String rarity,
                   boolean collectible,
                   List<String> keywords,
                   int cost,
                   int power,
                   int health,
                   String desc,
                   String lvlDesc,
                   List<String> formats,
                   String flavour,
                   List<String> artists,
                   List<String> regions) {
        this.cardCode = cardCode;
        this.type = type;
        this.subType = subType;
        this.rarity = rarity;
        this.collectible = collectible;
        this.keywords = keywords;
        this.cost = cost;
        this.power = power;
        this.health = health;
        this.desc = desc;
        this.lvlDesc = lvlDesc;
        this.formats = formats;
        this.flavour = flavour;
        this.artists = artists;
        this.regions = regions;
    }

}
