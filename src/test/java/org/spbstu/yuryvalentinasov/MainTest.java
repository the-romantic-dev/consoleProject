package org.spbstu.yuryvalentinasov;


import org.junit.jupiter.api.Test;
import org.kohsuke.args4j.CmdLineException;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


public class MainTest {

    private File getFileFromResources(String resource) throws URISyntaxException {
        URL url = getClass().getClassLoader().getResource(resource);
        return new File(url.toURI());
    }

    private String getTextFromFile(File file) throws IOException {
        return Files.readString(file.toPath());
    }

    @Test
    void outputTest() throws CmdLineException, URISyntaxException, IOException {
        String inputFileName = "testInput.txt";
        String outputFileName = "testOutput.txt";
        String output2FileName = "testOutput2.txt";

        File inputFile = getFileFromResources(inputFileName);
        File outputFile = getFileFromResources(outputFileName);
        File outputFile2 = getFileFromResources(output2FileName);


        String[] args1 = {"-c", "FFaaa", inputFile.getAbsolutePath(), "-o", outputFile.getAbsolutePath()};
        Main.main(args1);

        String[] args2 = {"-d", "FFaaa", outputFile.getAbsolutePath(), "-o", outputFile2.getAbsolutePath()};
        Main.main(args2);


        assertEquals(getTextFromFile(inputFile), getTextFromFile(outputFile2));
    }

    @Test
    void CmdLineExeptionsTest() throws URISyntaxException, IOException {
        File inputFile = File.createTempFile("testInput-", ".txt");
        File outputFile = File.createTempFile("testOutput-", ".txt");
        String incorrectFileName = "sdfawefadf.wwwda";

        String[] args1 = {"-c", "asdasd", inputFile.getAbsolutePath(), "-o", outputFile.getAbsolutePath()};
        String[] args2 = {inputFile.getAbsolutePath(), "-o", outputFile.getAbsolutePath()};
        String[] args3 = {"-c", "FF", "-o", outputFile.getAbsolutePath()};
        String[] args4 = {"-c", inputFile.getAbsolutePath(), "-o", outputFile.getAbsolutePath()};
        String[] args5 = {"-c", "11", incorrectFileName, "-o", outputFile.getAbsolutePath()};

        try {
            new CommandLineArgument(args1);
        } catch (IllegalArgumentException | CmdLineException e) {
            assertEquals("Your key is incorrect. Please input a hexadecimal num", e.getMessage());
        }

        try {
            new CommandLineArgument(args2);
        } catch (IllegalArgumentException | CmdLineException e) {
            assertEquals("Please input encoding or decoding key", e.getMessage());
        }

        try {
            new CommandLineArgument(args3);
        } catch (IllegalArgumentException | CmdLineException e) {
            assertEquals("Argument \"Input file name\" is required", e.getMessage());
        }
        try {
            new CommandLineArgument(args4);
        } catch (IllegalArgumentException | CmdLineException e) {
            assertEquals("Argument \"Input file name\" is required", e.getMessage());
        }
        try {
            new CommandLineArgument(args5);
        } catch (IllegalArgumentException | CmdLineException e) {
            assertEquals("Your input file doesn't exists", e.getMessage());
        }

    }


    @Test
    void encryptingTest() {
        String text = "Something in English и что-то на русском";
        byte[] key = {(byte) 0xf1, (byte) 0x80, (byte) 0xff, (byte) 0x11};

        byte[] encodingData = text.getBytes();
        byte[] encoded = Main.encrypt(encodingData, key);
        assertFalse(Arrays.equals(encodingData, encoded));
        byte[] decoded = Main.encrypt(encoded, key);
        assertTrue(Arrays.equals(encodingData, decoded));
    }


    @Test
    void xorTest() {
        byte input = 69;
        byte key = -15;
        byte b1 = Main.xor(input, key);
        byte b2 = Main.xor(b1, key);
        assertEquals(b2, input);
    }

}