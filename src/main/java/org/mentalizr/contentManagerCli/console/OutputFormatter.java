package org.mentalizr.contentManagerCli.console;

import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.AnsiFormat;
import com.diogonunes.jcolor.Attribute;

public class OutputFormatter {

    public enum OutputType { NORMAL, ERROR, OK}

    private static final AnsiFormat okFormat = new AnsiFormat(Attribute.GREEN_TEXT(), Attribute.BOLD());
    private static final AnsiFormat errorFormat = new AnsiFormat(Attribute.RED_TEXT(), Attribute.BOLD());
    private static final AnsiFormat programFormat = new AnsiFormat(Attribute.YELLOW_TEXT(), Attribute.BOLD());

    private final OutputType outputType;
    private final String tag;
    private final boolean hasProgramTag;

    public OutputFormatter(OutputType outputType, String tag, boolean hasProgramTag) {
        this.outputType = outputType;
        this.tag = tag;
        this.hasProgramTag = hasProgramTag;
    }

    public void write(OutputConfig outputConfig, String program, String message) {
        if (outputConfig.isCompletelySilent()) return;

        writeToConsole(outputConfig, program, message);
        writeToLogger(outputConfig, program, message);
    }

    public void write(OutputConfig outputConfig, String message) {
        if (this.hasProgramTag)
            throw new IllegalStateException("Wrong write method called. " + OutputFormatter.class.getSimpleName()
                    + " initialized for rendering program name.");
        if (outputConfig.isCompletelySilent()) return;

        String program = "";
        writeToConsole(outputConfig, program, message);
        writeToLogger(outputConfig, program, message);
    }

    private void writeToConsole(OutputConfig outputConfig, String program, String message) {
        if (!outputConfig.isToConsole()) return;

        if (outputConfig.isColorizedConsole()) {
            writeColorizedTypeTag(outputConfig);
            writeColorizedProgramTag(outputConfig, program);
        } else {
            writePlainTypeTag(outputConfig);
            writePlainProgramTag(outputConfig, program);
        }
        writeMessage(outputConfig, message);
    }

    private void writeColorizedTypeTag(OutputConfig outputConfig) {
        if (this.outputType == OutputType.NORMAL) return;

        String tag = "[" + this.tag + "] ";
        AnsiFormat ansiFormat;
        if (this.outputType == OutputType.OK) {
            ansiFormat = okFormat;
        } else {
            ansiFormat = errorFormat;
        }
        outputConfig.getOut().print(Ansi.colorize(tag, ansiFormat));
    }

    private void writeColorizedProgramTag(OutputConfig outputConfig, String program) {
        if (!this.hasProgramTag) return;

        String tag = "[" + program + "] ";
        outputConfig.getOut().print(Ansi.colorize(tag, programFormat));
    }

    private void writeMessage(OutputConfig outputConfig, String message) {
        outputConfig.getOut().println(message);
    }

    private void writePlainTypeTag(OutputConfig outputConfig) {
        if (this.outputType == OutputType.NORMAL) return;

        String tag = "[" + this.tag + "] ";
        outputConfig.getOut().print(tag);
    }

    private void writePlainProgramTag(OutputConfig outputConfig, String program) {
        if (!this.hasProgramTag) return;

        String tag = "[" + program + "] ";
        outputConfig.getOut().print(tag);
    }

    private void writeToLogger(OutputConfig outputConfig, String program, String message) {
        if (!outputConfig.isToLogger()) return;

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
        if (outputConfig.isToLogger()) {
            System.out.println("TO LOGGER: " + output);
        }
    }

}
