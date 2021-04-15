package org.mentalizr.contentManagerCli.console;

import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.AnsiFormat;
import com.diogonunes.jcolor.Attribute;
import org.mentalizr.contentManagerCli.executors.ExecutionSummary;

import java.util.Locale;

public class OutputFormatter {

    public enum OutputType { NORMAL, ERROR, OK, PROGRAM}

    private static final AnsiFormat okFormat = new AnsiFormat(Attribute.GREEN_TEXT(), Attribute.BOLD());
    private static final AnsiFormat errorFormat = new AnsiFormat(Attribute.RED_TEXT(), Attribute.BOLD());
    private static final AnsiFormat programFormat = new AnsiFormat(Attribute.BLUE_TEXT(), Attribute.BOLD());

    private final OutputType outputType;
    private final String tag;
    private final boolean hasProgramTag;

    public OutputFormatter(OutputType outputType, String tag, boolean hasProgramTag) {
        this.outputType = outputType;
        this.tag = tag;
        this.hasProgramTag = hasProgramTag;
    }

    public void write(ConsoleConfig consoleConfig, String program, String message) {
        if (consoleConfig.isCompletelySilent()) return;

        writeToConsole(consoleConfig, program, message);
        writeToLogger(consoleConfig, program, message);
    }

    public void write(ConsoleConfig consoleConfig, String message) {
        if (this.hasProgramTag)
            throw new IllegalStateException("Wrong write method called. " + OutputFormatter.class.getSimpleName()
                    + " initialized for rendering program name.");
        if (consoleConfig.isCompletelySilent()) return;

        String program = "";
        writeToConsole(consoleConfig, program, message);
        writeToLogger(consoleConfig, program, message);
    }

    public static void writeSummary(ConsoleConfig consoleConfig, ExecutionSummary executionSummary) {
        if (consoleConfig.isCompletelySilent()) return;
        writeSummaryToConsole(consoleConfig, executionSummary);
        // TODO writeSummaryToLog
    }

    private static void writeSummaryToConsole(ConsoleConfig consoleConfig, ExecutionSummary executionSummary) {
        if (!consoleConfig.isToConsole()) return;

        String operation = ("m7r content " + executionSummary.getOperation()).toUpperCase(Locale.ROOT);

        if (consoleConfig.isColorizedConsole()) {
            if (executionSummary.isSuccessful()) {
                consoleConfig.getOut().println();
                consoleConfig.getOut().println(Ansi.colorize(operation + " SUCCESSFUL", okFormat));
            } else {
                consoleConfig.getErrorOut().println();
                consoleConfig.getErrorOut().println(Ansi.colorize(operation + " FAILED", errorFormat));
            }
        } else {
            if (executionSummary.isSuccessful()) {
                consoleConfig.getOut().println(operation + " SUCCESSFUL");
            } else {
                consoleConfig.getErrorOut().println(operation + " FAILED");
            }
        }
        String summary = executionSummary.getTotalExecutions()
                + " program" + (executionSummary.getTotalExecutions() > 1 ? "s" : "") + " processed: "
                + executionSummary.getSuccessfulExecutions() + " ok, "
                + executionSummary.getFailedExecutions() + " failed.";
        if (executionSummary.isSuccessful()) {
            consoleConfig.getOut().println(summary);
        } else {
            consoleConfig.getErrorOut().println(summary);
        }
    }

    private void writeToConsole(ConsoleConfig consoleConfig, String program, String message) {
        if (!consoleConfig.isToConsole()) return;

        if (consoleConfig.isColorizedConsole()) {
            if (this.outputType != OutputType.PROGRAM) writeColorizedTypeTag(consoleConfig);
            writeColorizedProgramTag(consoleConfig, program);
        } else {
            if (this.outputType != OutputType.PROGRAM) writePlainTypeTag(consoleConfig);
            writePlainProgramTag(consoleConfig, program);
        }
        writeMessage(consoleConfig, message);
    }

    private void writeColorizedTypeTag(ConsoleConfig consoleConfig) {
        if (this.outputType == OutputType.NORMAL) return;

        String tag = "[" + this.tag + "] ";
        AnsiFormat ansiFormat;
        if (this.outputType == OutputType.OK) {
            ansiFormat = okFormat;
        } else {
            ansiFormat = errorFormat;
        }
        consoleConfig.getOut().print(Ansi.colorize(tag, ansiFormat));
    }

    private void writeColorizedProgramTag(ConsoleConfig consoleConfig, String program) {
        if (!this.hasProgramTag) return;

        String tag = "[" + program + "] ";
        consoleConfig.getOut().print(Ansi.colorize(tag, programFormat));
    }

    private void writeMessage(ConsoleConfig consoleConfig, String message) {
        consoleConfig.getOut().println(message);
    }

    private void writePlainTypeTag(ConsoleConfig consoleConfig) {
        if (this.outputType == OutputType.NORMAL) return;

        String tag = "[" + this.tag + "] ";
        consoleConfig.getOut().print(tag);
    }

    private void writePlainProgramTag(ConsoleConfig consoleConfig, String program) {
        if (!this.hasProgramTag) return;

        String tag = "[" + program + "] ";
        consoleConfig.getOut().print(tag);
    }

    private void writeToLogger(ConsoleConfig consoleConfig, String program, String message) {
        if (!consoleConfig.isToLogger()) return;

        String output = "";
        if (this.outputType != OutputType.NORMAL) {
            String tag = "[" + this.tag + "] ";
            output += tag;
        }
        if (this.hasProgramTag) {
            output += "[" + program + "] ";
        }
        output += message;

        // TODO write output
        if (consoleConfig.isToLogger()) {
            System.out.println("TO LOGGER: " + output);
        }
    }

}
