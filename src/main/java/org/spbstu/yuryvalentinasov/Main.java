package org.spbstu.yuryvalentinasov;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        CommandLineArgument values = new CommandLineArgument(args);
        int key = -1;
        if (values.eKey != null) key = stringToHex(values.eKey);
        else if (values.dKey != null) key = stringToHex(values.dKey);
        String result = "";

        try {
            result = encrypt(key, values.inputFileName, values.outputFileName);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        System.out.println(result);
    }

    private static int stringToHex(String key) {
        return Integer.parseInt(key, 16);
    }


    private static char xor(int a, int key) {
        return (char) (a ^ key);
    }

    public static String encrypt(int key,
                                 String inputFileName,
                                 String outputFileName) throws IOException {
        FileReader reader = new FileReader(inputFileName);

        StringBuilder input = new StringBuilder();
        StringBuilder result = new StringBuilder();

        char[] buffer = new char[16];
        int ch;
        while ((ch = reader.read(buffer)) != -1) {
            input.append(buffer, 0, ch);
        }
        for (char sym : input.toString().toCharArray()) {
            result.append(xor(sym, key));
        }
        reader.close();

        FileWriter writer = new FileWriter(outputFileName);
        writer.write(result.toString());
        writer.close();

        return result.toString();
    }
}
