package org.mentalizr.contentManagerCli.console.outputFormatter;

import org.mentalizr.contentManagerCli.console.ConsoleConfig;
import org.mentalizr.contentManagerCli.console.OutputFormatterNew;

import java.io.PrintStream;
import java.util.List;

public class OkProgramOutputFormatter extends OutputFormatterNew {

    public OkProgramOutputFormatter(ConsoleConfig consoleConfig) {
        super(consoleConfig);
    }

    public void out(String programName, String message) {
        super.out(Destination.OUT, message, programName);
    }

    @Override
    protected String getPlainString(String message, List<String> messageParameters) {
        return "[OK] [" + messageParameters.get(0) + "] " + message;
    }

    @Override
    protected void writeColorizedConsole(PrintStream printStream, String message, List<String> messageParameters) {
        printGreenTag(printStream, "OK");
        printStream.print(" ");
        printBlueTag(printStream, messageParameters.get(0));
        printStream.println(" ");
        printStream.println(message);
    }
}
