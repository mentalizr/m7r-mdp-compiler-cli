package org.mentalizr.contentManagerCli.console;

import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.AnsiFormat;
import com.diogonunes.jcolor.Attribute;

public class Output {

    private static final AnsiFormat okFormat = new AnsiFormat(Attribute.GREEN_TEXT(), Attribute.BOLD());
    private static final AnsiFormat errorFormat = new AnsiFormat(Attribute.RED_TEXT(), Attribute.BOLD());
    private static final AnsiFormat programFormat = new AnsiFormat(Attribute.YELLOW_TEXT(), Attribute.BOLD());

    private static OutputConfig outputConfig = null;

    public static void initialize(OutputConfig outputConfig) {
        Output.outputConfig = outputConfig;
    }

    public static void assertAsInitialized() {
        if (outputConfig == null)
            throw new IllegalStateException(Output.class.getSimpleName() + " is not initialized.");
    }

    public static void ok(String message) {
        assertAsInitialized();
        if (outputConfig.isCompletelySilent()) return;

        String ok = "[OK]";

        if (outputConfig.isToConsole()) {
            if (outputConfig.isColorizedConsole()) {
                outputConfig.getOut().print(Ansi.colorize(ok, okFormat));
                outputConfig.getOut().println(" " + message);
            } else {
                outputConfig.getOut().print(ok);
                outputConfig.getOut().println(" " + message);
            }
        }

        if (outputConfig.isToLogger()) {
            // TODO logger on info level
        }
    }

    public static void ok(String message, String programName) {
        assertAsInitialized();
        if (outputConfig.isCompletelySilent()) return;

        String ok = "[OK]";

        if (outputConfig.isToConsole()) {
            if (outputConfig.isColorizedConsole()) {
                outputConfig.getOut().print(Ansi.colorize(ok, okFormat));
                outputConfig.getOut().print(" ");
                outputConfig.getOut().print(Ansi.colorize("[" + programName + "]", programFormat));
                outputConfig.getOut().print(" ");
                outputConfig.getOut().println(message);
            } else {
                outputConfig.getOut().println(ok + " [" + programName + "] " + message);
            }
        }

        if (outputConfig.isToLogger()) {
            // TODO logger on info level
        }
    }

    private static void errorWithToken(String errorToken, String message) {
        assertAsInitialized();
        if (outputConfig.isCompletelySilent()) return;

        String error = "[" + errorToken + "]";

        if (outputConfig.isToConsole()) {
            if (outputConfig.isColorizedConsole()) {
                outputConfig.getErrorOut().print(Ansi.colorize(error, errorFormat));
                outputConfig.getErrorOut().println(" " + message);
            } else {
                outputConfig.getErrorOut().println(error + " " + message);
            }
        }

        if (outputConfig.isToLogger()) {
            // TODO logger on error level
        }
    }


    public static void error(String message) {
        errorWithToken("Error", message);
    }

    public static void internalError(String message) {
        errorWithToken("Internal error", message);
    }

    public static void out(String message) {
        assertAsInitialized();
        if (outputConfig.isCompletelySilent()) return;

        if (outputConfig.isToConsole()) {
            outputConfig.getOut().println(message);
        }

        if (outputConfig.isToLogger()) {
            // TODO logger on info level
        }
    }

}
