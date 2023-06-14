package io.sanchopansa.lolwiki.lorcardretrospective;

import picocli.CommandLine;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@CommandLine.Command(mixinStandardHelpOptions = true)
public class MainClass implements Runnable {
    @CommandLine.Parameters(index = "0", paramLabel = "Card code", description = "LoR Card ID")
    private String cardCode;

    @CommandLine.Option(
            names= {"-f", "--file"},
            defaultValue = CommandLine.Option.NULL_VALUE,
            fallbackValue = "retrospection.txt",
            paramLabel = "File",
            description = "Output file (optional), default value: ${FALLBACK-VALUE}",
            arity = "0..1"
    )
    private Path filepath;

    public static void main(String[] args) {
        new CommandLine(new MainClass()).execute(args);
    }

    @Override
    public void run() {
        System.out.println("Ныне и присно возношу молитву во имя Изменяющего Пути, Великого Заговорщика и Архитектора Судеб.");
        LoRCardRetrospector retrospector = new LoRCardRetrospector(this.cardCode);
        System.out.println(filepath);
        retrospector.getCardRetrospection();
        String result = retrospector.printCardHistory();
        if(this.filepath != null) {
            try(BufferedWriter bWriter = Files.newBufferedWriter(filepath, StandardCharsets.UTF_8, StandardOpenOption.CREATE)) {
                bWriter.write(result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
