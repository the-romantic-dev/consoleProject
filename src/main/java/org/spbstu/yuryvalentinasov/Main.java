package org.spbstu.yuryvalentinasov;

import org.kohsuke.args4j.CmdLineException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws CmdLineException {
        CommandLineArgument values = new CommandLineArgument(args);


        try (
                FileInputStream input = new FileInputStream(values.getInputFile());
                FileOutputStream output = new FileOutputStream(values.getOutputFile())
        ) {
            byte[] encodingData = input.readAllBytes();
            byte[] key = keyToByteArray(values.getKey());
            byte[] result = encrypt(encodingData, key);
            output.write(result);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }


    public static byte xor(byte a, byte b) {
        return (byte) (a ^ b);
    }

    public static byte[] encrypt(byte[] encodingData, byte[] key) {
        byte[] result = new byte[encodingData.length];
        int k = 0;
        for (int i = 0; i < encodingData.length; i++) {
            if (k >= key.length) k = 0;
            result[i] = (byte) (encodingData[i] ^ key[k]);
            k++;
        }
        return result;
    }

    private static byte[] keyToByteArray(String key) {
        byte[] result = new byte[key.length() / 2 + key.length() % 2];
        char[] oneByte = {'!', '!'};
        for (int i = 0; i < key.length(); i++) {
            oneByte[i % 2] = key.charAt(i);
            if (i % 2 != 0) {
                result[i / 2] = getByteFromKeyPart(oneByte);
                oneByte[0] = '!';
                oneByte[1] = '!';
            }
        }
        if (oneByte[0] != '!') result[result.length - 1] = getByteFromKeyPart(oneByte);
        return result;
    }


    public static byte getByteFromKeyPart(char[] keyPart) {
        int result;
        result = Character.digit(keyPart[0], 16);
        if (keyPart[1] != '!') {
            result = result << 4;
            result += Character.digit(keyPart[1], 16);
        }
        return (byte) result;
    }
}
