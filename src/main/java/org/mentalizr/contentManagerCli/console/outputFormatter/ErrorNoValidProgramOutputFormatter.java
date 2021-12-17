package org.mentalizr.contentManagerCli.console.outputFormatter;

import org.mentalizr.contentManagerCli.console.ConsoleConfig;
import org.mentalizr.contentManagerCli.console.OutputFormatterNew;

import java.io.PrintStream;
import java.util.List;

public class ErrorNoValidProgramOutputFormatter extends OutputFormatterNew {

    public ErrorNoValidProgramOutputFormatter(ConsoleConfig consoleConfig) {
        super(consoleConfig);
    }

    public void out(String programName, String message, Exception cause) {
        super.out(Destination.ERROR, message + " Program validation failed. Cause: " + cause.getMessage(), programName);
    }

    @Override
    protected String getPlainString(String message, List<String> messageParameters) {
        return "[Error] [" + messageParameters.get(0) + "] " + message;
    }

    @Override
    protected String getColorizedString(String message, List<String> messageParameters) {
        return getRedTag("Error") + " " + getBlueTag(messageParameters.get(0)) + " " + message;
    }

//    @Override
//    protected void writeColorizedConsole(PrintStream printStream, String message, List<String> messageParameters) {
//        printRedTag(printStream, "Error");
//        printStream.print(" ");
//        printBlueTag(printStream, messageParameters.get(0));
//        printStream.println(" ");
//        printStream.println(message);
//    }
}
