package org.mentalizr.contentManagerCli.console.outputFormatter;

import org.mentalizr.contentManagerCli.console.ConsoleConfig;
import org.mentalizr.contentManagerCli.console.OutputFormatterNew;

import java.io.PrintStream;
import java.util.List;

public class ErrorOutputFormatter extends OutputFormatterNew {

    public ErrorOutputFormatter(ConsoleConfig consoleConfig) {
        super(consoleConfig);
    }

    public void out(String message) {
        super.out(Destination.ERROR, message);
    }

    @Override
    protected String getPlainString(String message, List<String> messageParameters) {
        return "[Error] " + message;
    }

    @Override
    protected String getColorizedString(String message, List<String> messageParameters) {
        return getRedTag("Error") + " " + message;
    }

//    @Override
//    protected void writeColorizedConsole(PrintStream printStream, String message, List<String> messageParameters) {
//        printRedTag(printStream, "Error");
//        printStream.print(" ");
//        printStream.println(message);
//    }
}
