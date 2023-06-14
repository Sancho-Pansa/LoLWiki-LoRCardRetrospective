package io.sanchopansa.lolwiki.lorcardretrospective;

import picocli.CommandLine;

import java.io.File;

public class MainClass implements Runnable {
    @CommandLine.Option(names= {"-f", "--file"}, paramLabel = "File", description = "output file", arity = "0..1")
    private static File file;

    @CommandLine.Parameters(index = "0", paramLabel = "Card code", description = "LoR Card ID")
    private static String cardCode;
    public static void main(String[] args) {
        new CommandLine(new MainClass()).execute(args);
        System.out.println("Ныне и присно возношу молитву во имя Изменяющего Пути, Великого Заговорщика и Архитектора Судеб.");
        System.out.println(cardCode);
        LoRCardRetrospector retrospector = new LoRCardRetrospector("05IO003");
        //retrospector.getCardRetrospection();
        //retrospector.printCardHistory();
    }

    @Override
    public void run() {

    }
}
