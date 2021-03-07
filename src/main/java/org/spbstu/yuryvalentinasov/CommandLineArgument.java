package org.spbstu.yuryvalentinasov;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;


public class CommandLineArgument {
    @Option(name = "-c", usage = "Set key for encryption", forbids = "-d")
    public String eKey;

    @Option(name = "-d", usage = "Set key for decryption", forbids = "-c")
    public String dKey;

    @Argument(required = true)
    public String inputFileName;

    @Option(name = "-o", usage = "Set output file name", required = true)
    public String outputFileName;


    public CommandLineArgument(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
