package org.mentalizr.contentManagerCli.console.outputFormatter;

import org.junit.jupiter.api.Test;
import org.mentalizr.contentManagerCli.console.ConsoleConfig;
import org.mentalizr.contentManagerCli.console.ConsoleConfigBuilder;
import org.mentalizr.contentManagerCli.utils.Unicode;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;

class OkOutputFormatterTest {

    @Test
    void charTest() {

        String string = "test";

        for (int i = 0; i < string.length(); i++) {
            System.out.println(string.charAt(i));
        }

//        byte[] bytes = string.getBytes();
//        for (byte thisByte : bytes) {
//            System.out.println(thisByte);
//        }

    }

    @Test
    void out() {

        ByteArrayOutputStream outBAOS = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outBAOS);

        ConsoleConfig consoleConfig =
                new ConsoleConfigBuilder().withColorizedConsole(true).withOutputToPrintStream(out).build();

        OkOutputFormatter okOutputFormatter = new OkOutputFormatter(consoleConfig);

        String message = "This is a test message.";

        okOutputFormatter.out(message);

        String consoleOutput = outBAOS.toString();
        String consoleOutputEscaped = Unicode.escape(consoleOutput);

//        System.out.println(consoleOutput.length());

//        String string = consoleOutput;
//        for (int i = 0; i < string.length(); i++) {
//            System.out.print(string.charAt(i) + " ");
//        }
//        System.out.println();

        System.out.println("\u001B[32;1m[OK]\u001B[0m This is a test message.");

        System.out.println("Codepoint ad 0: " + consoleOutput.codePointAt(0));

        assertEquals("\\u001B[32;1m[OK]\\u001B[0m This is a test message.\\n", consoleOutputEscaped);

//        String a = "[OK] This is a test message.";
    }

    @Test
    void charactersTest() {

        System.out.println(System.getProperty("file.encoding"));
        String original = new String("A" + "\u00ea" + "\u00f1"
                + "\u00fc" + "C");

        System.out.println("original = " + original);
        System.out.println();

        try {
            byte[] utf8Bytes = original.getBytes("UTF8");
            byte[] defaultBytes = original.getBytes();

            String roundTrip = new String(utf8Bytes, "UTF8");
            System.out.println("roundTrip = " + roundTrip);

            System.out.println();
            printBytes(utf8Bytes, "utf8Bytes");
            System.out.println();
            printBytes(defaultBytes, "defaultBytes");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public static void printBytes(byte[] array, String name) {
        for (int k = 0; k < array.length; k++) {
            System.out.println(name + "[" + k + "] = " + "0x" +
                    byteToHex(array[k]));
        }
    }

    static public String byteToHex(byte b) {
        // Returns hex String representation of byte b
        char hexDigit[] = {
                '0', '1', '2', '3', '4', '5', '6', '7',
                '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
        };
        char[] array = { hexDigit[(b >> 4) & 0x0f], hexDigit[b & 0x0f] };
        return new String(array);
    }

    static public String charToHex(char c) {
        // Returns hex String representation of char c
        byte hi = (byte) (c >>> 8);
        byte lo = (byte) (c & 0xff);
        return byteToHex(hi) + byteToHex(lo);
    }

}