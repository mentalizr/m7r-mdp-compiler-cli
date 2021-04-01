package org.mentalizr.contentManagerCli.console;

import org.mentalizr.contentManagerCli.executors.ExecutionSummary;

public class Console {

    private static ConsoleConfig consoleConfig = null;

    public static void initialize(ConsoleConfig consoleConfig) {
        Console.consoleConfig = consoleConfig;
    }

    public static void assertAsInitialized() {
        if (consoleConfig == null)
            throw new IllegalStateException(Console.class.getSimpleName() + " is not initialized.");
    }

    public static void out(OutputFormatter outputFormatter, String program, String message) {
        assertAsInitialized();
        outputFormatter.write(consoleConfig, program, message);
    }

    public static void out(OutputFormatter outputFormatter, String message) {
        assertAsInitialized();
        outputFormatter.write(consoleConfig, message);
    }

    public static void summaryOut(ExecutionSummary executionSummary) {
        assertAsInitialized();
        OutputFormatter.writeSummary(consoleConfig, executionSummary);
    }

    public static void stacktrace(Exception e) {
        assertAsInitialized();
        e.printStackTrace(consoleConfig.getErrorOut());
    }

}
