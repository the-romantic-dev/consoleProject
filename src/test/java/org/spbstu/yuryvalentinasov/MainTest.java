package org.spbstu.yuryvalentinasov;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class MainTest extends TestCase {
    @Test
    void main() {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            PrintStream old = System.out;
            PrintStream ps = new PrintStream(baos);
            System.setOut(ps);


            String inputFile = "C:\\Users\\AHXPL\\IdeaProjects\\consoleProject_ciphxor" +
                    "\\src\\main\\java\\org\\spbstu\\yuryvalentinasov\\input.txt";
            String outputFile = "C:\\Users\\AHXPL\\IdeaProjects\\consoleProject_ciphxor\\src\\" +
                    "main\\java\\org\\spbstu\\yuryvalentinasov\\output.txt";
            String[] args = {"-c", "AF", inputFile, "-o", outputFile};
            Main.main(args);


            System.out.flush();
            System.setOut(old);
            System.out.println("+" + baos.toString());
    }

}