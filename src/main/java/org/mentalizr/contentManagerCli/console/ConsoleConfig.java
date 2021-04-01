package org.mentalizr.contentManagerCli.console;

import java.io.PrintStream;

public class ConsoleConfig {

    private final boolean toConsole;
    private final boolean colorizedConsole;
    private final boolean toLogger;
    private final String loggerName;
    private final PrintStream out;
    private final PrintStream errorOut;

    public ConsoleConfig(boolean toConsole, boolean colorizedConsole, boolean toLogger, String loggerName, PrintStream out, PrintStream errorOut) {
        this.toConsole = toConsole;
        this.colorizedConsole = colorizedConsole;
        this.toLogger = toLogger;
        this.loggerName = loggerName;
        this.out = out;
        this.errorOut = errorOut;
    }

    public boolean isToConsole() {
        return toConsole;
    }

    public boolean isColorizedConsole() {
        return colorizedConsole;
    }

    public boolean isToLogger() {
        return toLogger;
    }

    public String getLoggerName() {
        return loggerName;
    }

    public PrintStream getOut() {
        return out;
    }

    public PrintStream getErrorOut() {
        return errorOut;
    }

    public boolean isCompletelySilent() {
        return !this.toConsole && !this.toLogger;
    }
}
