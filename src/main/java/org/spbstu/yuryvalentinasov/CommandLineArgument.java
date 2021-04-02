package org.spbstu.yuryvalentinasov;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;
import java.io.IOException;


public class CommandLineArgument {
    private String key;

    @Option(name = "-c", usage = "Set key for encryption", forbids = "-d", metaVar = "Encoding key")
    private String eKey;

    @Option(name = "-d", usage = "Set key for decryption", forbids = "-c", metaVar = "Decoding key")
    private String dKey;

    public String getKey(){
        return key;
    }

    @Argument(required = true, usage = "Print input file name", metaVar = "Input file name")
    private File inputFile;

    public File getInputFile() {
        return inputFile;
    }

    @Option(name = "-o", usage = "Set output file name")
    private File outputFile;

    public File getOutputFile() {
        if (outputFile == null) {
            String outputFileName = inputFile.getName() + getFileExtension(inputFile);
            outputFile = new File(outputFileName);
        }
        try {
            outputFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputFile;
    }

    private String getFileExtension(File file) {
        String fileName = file.getName();
        StringBuilder sb = new StringBuilder();
        int i = fileName.length() - 1;
        while (fileName.charAt(i) != '.' && i > 0) {
            sb.insert(0, fileName.charAt(i));
            i--;
        }
        sb.insert(0, '.');
        return sb.toString();
    }

    public CommandLineArgument(String[] args) throws CmdLineException, IllegalArgumentException {
        CmdLineParser parser = new CmdLineParser(this);
        parser.parseArgument(args);

        if (eKey != null) key = eKey;
        else if (dKey != null) key = dKey;
        else {
            throw new IllegalArgumentException("Please input encoding or decoding key");
        }
        if (!isStringHex(key)) {
            throw new IllegalArgumentException("Your key is incorrect. Please input a hexadecimal num");
        }
        if (!inputFile.exists()) {
            throw new IllegalArgumentException("Your input file doesn't exists");
        }
    }

    private boolean isStringHex(String key) {
        return key.matches("[0-9a-fA-F]+");
    }
}
