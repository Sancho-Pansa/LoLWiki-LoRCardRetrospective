package io.sanchopansa.lolwiki.lorcardretrospective;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
public class LoRCard {
    private String cardCode;
    private String cardName;
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
    private String artists;

    private List<String> regions;

    public LoRCard() {

    }
    public LoRCard(String cardCode,
                   String cardName,
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
                   String artists,
                   List<String> regions) {
        this.cardCode = cardCode;
        this.cardName = cardName;
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

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getSubType() {
        return subType;
    }

    public void setSubType(List<String> subType) {
        this.subType = subType;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public boolean isCollectible() {
        return collectible;
    }

    public void setCollectible(boolean collectible) {
        this.collectible = collectible;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLvlDesc() {
        return lvlDesc;
    }

    public void setLvlDesc(String lvlDesc) {
        this.lvlDesc = lvlDesc;
    }

    public List<String> getFormats() {
        return formats;
    }

    public void setFormats(List<String> formats) {
        this.formats = formats;
    }

    public String getFlavour() {
        return flavour;
    }

    public void setFlavour(String flavour) {
        this.flavour = flavour;
    }

    public String getArtists() {
        return artists;
    }

    public void setArtists(String artists) {
        this.artists = artists;
    }

    public List<String> getRegions() {
        return regions;
    }

    public void setRegions(List<String> regions) {
        this.regions = regions;
    }

    public boolean deepEquals(LoRCard card) {
        return this.cardCode.equals(card.cardCode) &&
                this.cardName.equals(card.cardName) &&
                this.type.equals(card.type) &&
                this.subType.equals(card.subType) &&
                this.rarity.equals(card.rarity) &&
                this.keywords.equals(card.keywords) &&
                this.lvlDesc.equals(card.lvlDesc) &&
                this.formats.equals(card.formats) &&
                this.flavour.equals(card.flavour) &&
                this.artists.equals(card.artists) &&
                this.regions.equals(card.regions) &&
                this.collectible == card.collectible &&
                this.cost == card.cost &&
                this.power == card.power &&
                this.health == card.health &&
                this.desc.equals(card.desc);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoRCard loRCard = (LoRCard) o;
        return Objects.equals(cardCode, loRCard.cardCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardCode);
    }

    @Override
    public String toString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder
                .append(String.format("ID: %s\n", this.cardCode))
                .append(String.format("\tName: %s\n", this.cardName))
                .append(String.format("\tCollectible: %s\n", this.collectible))
                .append(String.format("\tRarity: %s\n", this.rarity))
                .append(String.format("\tType: %s\n", this.type))
                .append(String.format("\tSubtype: %s\n", this.subType.isEmpty() ? "(none)" : this.subType.toString()))
                .append(String.format("\tSubtype: %s\n", this.keywords.isEmpty() ? "(none)" : this.keywords.toString()))
                .append(String.format("\tCost: %d\n", this.cost))
                .append(String.format("\tPower: %d\n", this.power))
                .append(String.format("\tHealth: %d\n", this.health))
                .append(String.format("\tPower: %d\n", this.power))
                .append(String.format("\tDescription: %s\n", this.desc))
                .append(this.lvlDesc.equals("") ? String.format("\tLevel Up Description: %s\n", this.lvlDesc) : "")
                .append(String.format("\tFlavour: %s\n", this.flavour))
                .append(String.format("\tArtist: %s\n", this.artists));
        return sBuilder.toString();
    }

    public static class cardBuilder {
        private final LoRCard newCard;

        public cardBuilder() {
            newCard = new LoRCard();
        }
        public cardBuilder cardCode(String cardCode) {
            this.newCard.cardCode = cardCode;
            return this;
        }

        public cardBuilder cardName(String cardName) {
            this.newCard.cardName = cardName;
            return this;
        }

        public cardBuilder type(String type) {
            this.newCard.type = type;
            return this;
        }

        public cardBuilder subType(List<String> subType) {
            this.newCard.subType = subType;
            return this;
        }

        public cardBuilder rarity(String rarity) {
            this.newCard.rarity = rarity;
            return this;
        }

        public cardBuilder collectible(boolean collectible) {
            this.newCard.collectible = collectible;
            return this;
        }

        public cardBuilder keywords(List<String> keywords) {
            this.newCard.keywords = keywords;
            return this;
        }

        public cardBuilder cost(int cost) {
            this.newCard.cost = cost;
            return this;
        }

        public cardBuilder health(int health) {
            this.newCard.health = health;
            return this;
        }

        public cardBuilder power(int power) {
            this.newCard.power = power;
            return this;
        }

        public cardBuilder desc(String desc) {
            this.newCard.desc = desc;
            return this;
        }

        public cardBuilder lvlDesc(String lvlDesc) {
            this.newCard.lvlDesc = lvlDesc;
            return this;
        }

        public cardBuilder formats(List<String> formats) {
            this.newCard.formats = formats;
            return this;
        }

        public cardBuilder flavour(String flavour) {
            this.newCard.flavour = flavour;
            return this;
        }

        public cardBuilder artists(String artists) {
            this.newCard.artists = artists;
            return this;
        }

        public cardBuilder regions(List<String> regions) {
            this.newCard.regions = regions;
            return this;
        }

        public LoRCard build() {
            if(this.newCard.subType == null)
                this.newCard.subType = new ArrayList<>(0);
            if(this.newCard.keywords == null)
                this.newCard.keywords = new ArrayList<>(0);
            if(this.newCard.formats == null)
                this.newCard.formats = new ArrayList<>(0);
            if(this.newCard.regions == null)
                this.newCard.regions = new ArrayList<>(1);
            return this.newCard;
        }
    }
}
