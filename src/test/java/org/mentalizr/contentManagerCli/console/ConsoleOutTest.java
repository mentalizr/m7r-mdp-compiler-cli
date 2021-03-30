package org.mentalizr.contentManagerCli.console;

import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.AnsiFormat;
import com.diogonunes.jcolor.Attribute;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleOutTest {

    @Test
    void plausibilityTest() {

        ConsoleOut consoleOut = new ConsoleOut();

        String output = "Hallo " + consoleOut.red() + " world!";

    }

    @Test
    void jColorTest() {

        AnsiFormat greenFormat = new AnsiFormat(Attribute.GREEN_TEXT(), Attribute.BOLD());
        System.out.println(Ansi.colorize("[OK]", greenFormat));

    }

}