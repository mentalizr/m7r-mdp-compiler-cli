package org.mentalizr.contentManagerCli.console;

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

}
