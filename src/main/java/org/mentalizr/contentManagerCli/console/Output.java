package org.mentalizr.contentManagerCli.console;

import org.mentalizr.contentManagerCli.executors.ExecutionSummary;

public class Output {

    private static OutputConfig outputConfig = null;

    public static void initialize(OutputConfig outputConfig) {
        Output.outputConfig = outputConfig;
    }

    public static void assertAsInitialized() {
        if (outputConfig == null)
            throw new IllegalStateException(Output.class.getSimpleName() + " is not initialized.");
    }

    public static void out(OutputFormatter outputFormatter, String program, String message) {
        assertAsInitialized();
        outputFormatter.write(outputConfig, program, message);
    }

    public static void out(OutputFormatter outputFormatter, String message) {
        assertAsInitialized();
        outputFormatter.write(outputConfig, message);
    }

    public static void summaryOut(ExecutionSummary executionSummary) {
        assertAsInitialized();
        OutputFormatter.writeSummary(outputConfig, executionSummary);
    }

    public static void stacktrace(Exception e) {
        assertAsInitialized();
        e.printStackTrace(outputConfig.getErrorOut());
    }

}
