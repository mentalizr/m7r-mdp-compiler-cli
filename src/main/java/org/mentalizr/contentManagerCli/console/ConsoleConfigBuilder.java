package org.mentalizr.contentManagerCli.console;

import java.io.PrintStream;

@SuppressWarnings({"JavaDoc", "UnusedReturnValue"})
public class ConsoleConfigBuilder {

    private boolean toConsole = true;
    private boolean colorizedConsole = true;
    private boolean toLogger = false;
    private String loggerName = "org.mentalizr.contentManagerCli";
    private PrintStream out = System.out;
    private PrintStream errorOut = System.err;

    /**
     * Specifies whether output is printed on console. Default is true.
     *
     * @param toConsole
     * @return Current instance to be used as fluent API.
     */
    public ConsoleConfigBuilder withOutputToConsole(boolean toConsole) {
        this.toConsole = toConsole;
        return this;
    }

    /**
     * Specifies whether console output is colorized. Default is true.
     *
     * @param colorizedConsole
     * @return Current instance to be used as fluent API.
     */
    public ConsoleConfigBuilder withColorizedConsole(boolean colorizedConsole) {
        this.colorizedConsole = colorizedConsole;
        return this;
    }

    /**
     * Specifies whether output is printed on logger. Default is false.
     *
     * @param toLogger
     * @return Current instance to be used as fluent API.
     */
    public ConsoleConfigBuilder withOutputToLogger(boolean toLogger) {
        this.toLogger = toLogger;
        return this;
    }

    /**
     * Specifies the logger name to be logged on. Printing to logger requires
     * {@link ConsoleConfigBuilder#withOutputToLogger(boolean)} to be set to true.
     * Default is org.mentalizr.contentManagerCli.
     *
     * @param loggerName
     * @return Current instance to be used as fluent API.
     */
    public ConsoleConfigBuilder withLoggerName(String loggerName) {
        this.loggerName = loggerName;
        return this;
    }

    /**
     * Specifies {@link PrintStream} for console standard output.
     *
     * @param out
     * @return Current instance to be used as fluent API.
     */
    public ConsoleConfigBuilder withOutputToPrintStream(PrintStream out) {
        this.out = out;
        return this;
    }

    /**
     * Specifies {@link PrintStream} for console standard error output.
     *
     * @param errorOut
     * @return Current instance to be used as fluent API.
     */
    public ConsoleConfigBuilder withErrorOutputToPrintStream(PrintStream errorOut) {
        this.errorOut = errorOut;
        return this;
    }

    /**
     * Builds {@link ConsoleConfig} instance.
     *
     * @return ConsoleConfig instance.
     */
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
