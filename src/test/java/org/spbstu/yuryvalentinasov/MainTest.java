package org.spbstu.yuryvalentinasov;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MainTest {
    @Test
    void mainTest() {
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

    @Test
    void encryptingTest() {
        String encodingStr = "Автобус";

        String str1 = Main.encrypt(0xff, encodingStr);
        String str2 = Main.encrypt(0xff, str1);

        assertEquals(str2, encodingStr);
    }

    @Test
    void xorTest() {
        char sym = '\u1231';
        char sym2 = Main.xor(sym, 0xff);
        assertEquals(sym, Main.xor(sym2, 0xff));
    }

    @Test
    void stringToHex() {
        String hex = "ffff";
        assertEquals(0xffff, Main.stringToHex(hex));

        String notHex = "-opgr";
        assertThrows(NumberFormatException.class, () -> Main.stringToHex(notHex));
    }
}