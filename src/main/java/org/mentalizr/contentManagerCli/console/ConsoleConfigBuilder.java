package org.mentalizr.contentManagerCli.console;

import java.io.PrintStream;

public class ConsoleConfigBuilder {

    private boolean toConsole = true;
    private boolean colorizedConsole = true;
    private boolean toLogger = false;
    private String loggerName = "org.mentalizr.contentManagerCli";
    private PrintStream out = System.out;
    private PrintStream errorOut = System.err;

    public ConsoleConfigBuilder withOutputToConsole(boolean toConsole) {
        this.toConsole = toConsole;
        return this;
    }

    public ConsoleConfigBuilder withColorizedConsole(boolean colorizedConsole) {
        this.colorizedConsole = colorizedConsole;
        return this;
    }

    public ConsoleConfigBuilder withOutputToLogger(boolean toLogger) {
        this.toLogger = toLogger;
        return this;
    }

    public ConsoleConfigBuilder withLoggerName(String loggerName) {
        this.loggerName = loggerName;
        return this;
    }

    public ConsoleConfigBuilder withOutputToPrintStream(PrintStream out) {
        this.out = out;
        return this;
    }

    public ConsoleConfigBuilder withErrorOutputToPrintStream(PrintStream errorOut) {
        this.errorOut = errorOut;
        return this;
    }

    public ConsoleConfig build() {
        return new ConsoleConfig(
                this.toConsole,
                this.colorizedConsole,
                this.toLogger,
                this.loggerName,
                this.out,
                this.errorOut
        );
    }

}
