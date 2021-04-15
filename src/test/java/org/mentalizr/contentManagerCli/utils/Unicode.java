package org.mentalizr.contentManagerCli.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Unicode {

    public static String escape(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            int codePoint = string.codePointAt(i);
            if (codePoint == 13) {
                stringBuilder.append("\\r");
            } else if (codePoint == 10) {
                stringBuilder.append("\\n");
            } else if (codePoint < 32 || (codePoint >= 128 && codePoint <= 160)) {
                stringBuilder.append(toUnicodeHex(codePoint));
            } else {
                stringBuilder.append(string.charAt(i));
            }
        }
        return stringBuilder.toString();
    }

    public static String toUnicodeHex(int codepoint) {
        String hex = Integer.toHexString(codepoint).toUpperCase();
        StringBuilder stringBuilder = new StringBuilder(hex);
        for (int i = 1; i <= 4 - hex.length(); i++) {
            stringBuilder.insert(0, "0");
        }
        stringBuilder.insert(0, "\\u");
        return stringBuilder.toString();
    }

    @Test
    void testToUnicodeHex0() {
        assertEquals("\\u0000", toUnicodeHex(0));
    }

    @Test
    void testToUnicodeHex15() {
        assertEquals("\\u000F", toUnicodeHex(15));
    }

    @Test
    void testToUnicodeHex16() {
        assertEquals("\\u0010", toUnicodeHex(16));
    }

    @Test
    void testToUnicodeHex33() {
        assertEquals("\\u0021", toUnicodeHex(33));
    }

    @Test
    void testToUnicodeHex256() {
        assertEquals("\\u0100", toUnicodeHex(256));
    }

    @Test
    void testToUnicodeHex4096() {
        assertEquals("\\u1000", toUnicodeHex(4096));
    }


    @Test
    void testSimple() {
        String string = "Hello world!";
        assertEquals(string, escape(string));
    }

    @Test
    void testColorized() {
        String string = "\u001B[32;1m[OK]\u001B[0m This is a test message.";
        String expectedString = "\\u001B[32;1m[OK]\\u001B[0m This is a test message.";
        assertEquals(expectedString, escape(string));
    }

    @Test
    void testCodepoint03() {
        String string = "test\u0003test";
        String expectedString = "test\\u0003test";
        assertEquals(expectedString, escape(string));
    }

    @Test
    void testCodepoint1F() {
        String string = "test\u001Ftest";
        String expectedString = "test\\u001Ftest";
        assertEquals(expectedString, escape(string));
    }

    @Test
    void testCodepointA0() {
        String string = "test\u00A0test";
        String expectedString = "test\\u00A0test";
        assertEquals(expectedString, escape(string));
    }

    @Test
    void testNewline() {
        String string = "Test\nTest";
        String expectedString = "Test\\nTest";
        assertEquals(expectedString, escape(string));
    }

    @Test
    void testCR() {
        String string = "Test\rTest";
        String expectedString = "Test\\rTest";
        assertEquals(expectedString, escape(string));
    }

}
