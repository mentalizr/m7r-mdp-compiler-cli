package org.mentalizr.contentManagerCli.console.outputFormatter;

import org.mentalizr.contentManagerCli.console.ConsoleConfig;
import org.mentalizr.contentManagerCli.console.OutputFormatterNew;

import java.io.PrintStream;
import java.util.List;

public class RedOutputFormatter extends OutputFormatterNew {

    public RedOutputFormatter(ConsoleConfig consoleConfig) {
        super(consoleConfig);
    }

    public void out(String message) {
        super.out(Destination.ERROR, message);
    }

    @Override
    protected String getPlainString(String message, List<String> messageParameters) {
        return message;
    }

    @Override
    protected String getColorizedString(String message, List<String> messageParameters) {
        return getRedText(message);
    }

//    @Override
//    protected void writeColorizedConsole(PrintStream printStream, String message, List<String> messageParameters) {
//        printRedText(printStream, message);
//    }
}
