package org.mentalizr.contentManagerCli.console.outputFormatter;

import org.mentalizr.contentManagerCli.console.ConsoleConfig;
import org.mentalizr.contentManagerCli.console.OutputFormatterNew;

import java.io.PrintStream;
import java.util.List;

public class InternalErrorOutputFormatter extends OutputFormatterNew {

    public InternalErrorOutputFormatter(ConsoleConfig consoleConfig) {
        super(consoleConfig);
    }

    public void out(String message) {
        super.out(Destination.ERROR, message);
    }

    @Override
    protected String getPlainString(String message, List<String> messageParameters) {
        return "[Internal Error] " + message;
    }

    @Override
    protected void writeColorizedConsole(PrintStream printStream, String message, List<String> messageParameters) {
        printRedTag(printStream, "Internal Error");
        printStream.print(" ");
        printStream.println(message);
    }
}
