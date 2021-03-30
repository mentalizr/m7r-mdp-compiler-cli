package org.mentalizr.contentManagerCli.console;

import java.io.PrintStream;

public class OutputConfigBuilder {

    private boolean toConsole = true;
    private boolean colorizedConsole = true;
    private boolean toLogger = false;
    private String loggerName = "org.mentalizr.contentManagerCli";
    private PrintStream out = System.out;
    private PrintStream errorOut = System.err;

    public OutputConfigBuilder withOutputToConsole(boolean toConsole) {
        this.toConsole = toConsole;
        return this;
    }

    public OutputConfigBuilder withColorizedConsole(boolean colorizedConsole) {
        this.colorizedConsole = colorizedConsole;
        return this;
    }

    public OutputConfigBuilder withOutputToLogger(boolean toLogger) {
        this.toLogger = toLogger;
        return this;
    }

    public OutputConfigBuilder withLoggerName(String loggerName) {
        this.loggerName = loggerName;
        return this;
    }

    public OutputConfigBuilder withOutputToPrintStream(PrintStream out) {
        this.out = out;
        return this;
    }

    public OutputConfigBuilder withErrorOutputToPrintStream(PrintStream errorOut) {
        this.errorOut = errorOut;
        return this;
    }

    public OutputConfig build() {
        return new OutputConfig(
                this.toConsole,
                this.colorizedConsole,
                this.toLogger,
                this.loggerName,
                this.out,
                this.errorOut
        );
    }

}
