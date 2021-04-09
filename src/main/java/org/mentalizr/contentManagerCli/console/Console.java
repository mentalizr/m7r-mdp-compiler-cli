package org.mentalizr.contentManagerCli.console;

import org.mentalizr.contentManagerCli.console.outputFormatter.*;
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

    public static void out(String message) {
        assertAsInitialized();
        new NormalOutputFormatter(consoleConfig).out(message);
    }

    public static void programOut(String program, String message) {
        assertAsInitialized();
        new ProgramOutputFormatter(consoleConfig).out(program, message);
    }

    public static void okProgramOut(String program, String message) {
        assertAsInitialized();
        new OkProgramOutputFormatter(consoleConfig).out(program, message);
    }

    public static void okOut(String message) {
        assertAsInitialized();
        new OkOutputFormatter(consoleConfig).out(message);
    }

    public static void errorOut(String message) {
        assertAsInitialized();
        new ErrorOutputFormatter(consoleConfig).out(message);
    }

    public static void errorProgramOut(String program, String message) {
        assertAsInitialized();
        new ErrorProgramOutputFormatter(consoleConfig).out(program, message);
    }

    public static void internalErrorOut(String message) {
        assertAsInitialized();
        new InternalErrorOutputFormatter(consoleConfig).out(message);
    }

    public static void errorNoValidProgramOut(String program, String message, Exception cause) {
        assertAsInitialized();
        new ErrorNoValidProgramOutputFormatter(consoleConfig).out(program, message, cause);
    }

//    public static void out(OutputFormatter outputFormatter, String program, String message) {
//        assertAsInitialized();
//        outputFormatter.write(consoleConfig, program, message);
//    }
//
//    public static void out(OutputFormatter outputFormatter, String message) {
//        assertAsInitialized();
//        outputFormatter.write(consoleConfig, message);
//    }

    public static void summaryOut(ExecutionSummary executionSummary) {
        assertAsInitialized();
        OutputFormatter.writeSummary(consoleConfig, executionSummary);
    }

    public static void stacktrace(Exception e) {
        assertAsInitialized();
        e.printStackTrace(consoleConfig.getErrorOut());
    }

}
