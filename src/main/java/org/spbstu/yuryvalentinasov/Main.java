package org.spbstu.yuryvalentinasov;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        CommandLineArgument values = new CommandLineArgument(args);


        try {
            int key = -1;
            if (values.eKey != null) key = stringToHex(values.eKey);
            else if (values.dKey != null) key = stringToHex(values.dKey);
            StringBuilder encodingText = new StringBuilder();

            Files.lines(Paths.get(values.inputFileName)).forEach(line -> encodingText.append(line).append("\n"));
            String result = encrypt(key, encodingText.toString());

            BufferedWriter writer = new BufferedWriter(new FileWriter(values.outputFileName));
            writer.write(result);
            writer.close();

            System.out.println(result);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        catch (NumberFormatException e) {
            System.err.println("Your encoding key is incorrect. It should be hex number.");
        }

    }

    public static int stringToHex(String key) {
        return Integer.parseInt(key, 16);
    }


    public static char xor(char a, int key) {
        return (char) (a ^ key);
    }

    public static String encrypt(int key,
                                 String encodingText) {
        StringBuilder result = new StringBuilder();

        char[] charsInText = encodingText.toCharArray();
        for (char sym: charsInText) {
            result.append(xor(sym, key));
        }
        return result.toString();
    }
}
