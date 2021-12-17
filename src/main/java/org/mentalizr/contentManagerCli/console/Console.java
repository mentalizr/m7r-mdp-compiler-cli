package org.mentalizr.contentManagerCli.console;

import org.mentalizr.contentManagerCli.console.outputFormatter.*;
import org.mentalizr.contentManagerCli.executors.ExecutionSummary;

public class Console {

    private static ConsoleConfig consoleConfig = null;

    public static void initialize(ConsoleConfig consoleConfig) {
        Console.consoleConfig = consoleConfig;
    }

    public static void assertInitialized() {
        if (consoleConfig == null)
            throw new IllegalStateException(Console.class.getSimpleName() + " is not initialized.");
    }

    public static void out(String message) {
        assertInitialized();
        new NormalOutputFormatter(consoleConfig).out(message);
    }

    public static void programOut(String program, String message) {
        assertInitialized();
        new ProgramOutputFormatter(consoleConfig).out(program, message);
    }

    public static void okProgramOut(String program, String message) {
        assertInitialized();
        new OkProgramOutputFormatter(consoleConfig).out(program, message);
    }

    public static void okOut(String message) {
        assertInitialized();
        new OkOutputFormatter(consoleConfig).out(message);
    }

    public static void errorOut(String message) {
        assertInitialized();
        new ErrorOutputFormatter(consoleConfig).out(message);
    }

    public static void errorProgramOut(String program, String message) {
        assertInitialized();
        new ErrorProgramOutputFormatter(consoleConfig).out(program, message);
    }

    public static void internalErrorOut(String message) {
        assertInitialized();
        new InternalErrorOutputFormatter(consoleConfig).out(message);
    }

    public static void warnOut(String message) {
        assertInitialized();
        new WarnOutputFormatter(consoleConfig).out(message);
    }


    public static void errorNoValidProgramOut(String program, String message, Exception cause) {
        assertInitialized();
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
        assertInitialized();
        OutputFormatter.writeSummary(consoleConfig, executionSummary);
    }

    public static void stacktrace(Exception e) {
        assertInitialized();
        e.printStackTrace(consoleConfig.getErrorOut());
    }

}
