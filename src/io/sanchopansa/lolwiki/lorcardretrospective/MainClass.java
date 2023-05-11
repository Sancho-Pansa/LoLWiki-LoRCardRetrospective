package io.sanchopansa.lolwiki.lorcardretrospective;

public class MainClass {
    public static void main(String[] args) {
        System.out.println("Ныне и присно возношу молитву во имя Изменяющего Пути, Великого Заговорщика и Архитектора Судеб.");
        LoRCardRetrospector retrospector = new LoRCardRetrospector("01DE009");
        System.out.println(retrospector.getCardRetrospection());
    }
}
