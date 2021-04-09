package org.mentalizr.contentManagerCli.console.outputFormatter;

import org.mentalizr.contentManagerCli.console.ConsoleConfig;
import org.mentalizr.contentManagerCli.console.OutputFormatterNew;

import java.io.PrintStream;
import java.util.List;

public class GreenOutputFormatter extends OutputFormatterNew {

    public GreenOutputFormatter(ConsoleConfig consoleConfig) {
        super(consoleConfig);
    }

    public void out(String message) {
        super.out(Destination.OUT, message);
    }

    @Override
    protected String getPlainString(String message, List<String> messageParameters) {
        return message;
    }

    @Override
    protected void writeColorizedConsole(PrintStream printStream, String message, List<String> messageParameters) {
        printGreenText(printStream, message);
    }
}
