package org.mentalizr.contentManagerCli.console;

import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.AnsiFormat;
import com.diogonunes.jcolor.Attribute;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

public abstract class OutputFormatterNew {

    private static final AnsiFormat greenText = new AnsiFormat(Attribute.GREEN_TEXT(), Attribute.BOLD());
    private static final AnsiFormat redText = new AnsiFormat(Attribute.RED_TEXT(), Attribute.BOLD());
    private static final AnsiFormat blueText = new AnsiFormat(Attribute.BLUE_TEXT(), Attribute.BOLD());

    public enum Destination { OUT, ERROR }

    protected final ConsoleConfig consoleConfig;

    public OutputFormatterNew(ConsoleConfig consoleConfig) {
        this.consoleConfig = consoleConfig;
    }

    protected void out(Destination destination, String message, String... messageParameter) {
        List<String> messageParameters = Arrays.asList(messageParameter);
        if (consoleConfig.isCompletelySilent()) return;
        if (consoleConfig.isToConsole()) {
            if (consoleConfig.isColorizedConsole()) {
                writeColorizedConsole(destination, message, messageParameters);
            } else {
                writeToPlainConsole(destination, message, messageParameters);
            }
        }
        if (consoleConfig.isToLogger()) {
            writeToLogger(destination, message, messageParameters);
        }
    }

    protected abstract String getPlainString(String message, List<String> messageParameters);

    protected abstract String getColorizedString(String message, List<String> messageParameters);

//    protected abstract void writeColorizedConsole(PrintStream printStream, String message, List<String> messageParameters);

    private void writeToLogger(Destination destination, String message, List<String> messageParameters) {
        // TODO
    }

    private void writeToPlainConsole(Destination destination, String message, List<String> messageParameters) {
        String outputString = getPlainString(message, messageParameters);
        PrintStream out = getPrintStream(destination);
        out.println(outputString);
    }

    private void writeColorizedConsole(Destination destination, String message, List<String> messageParameters) {
        String outputString = getColorizedString(message, messageParameters);
        PrintStream out = getPrintStream(destination);
        out.println(outputString);
    }

    private PrintStream getPrintStream(Destination destination) {
        if (destination == Destination.OUT) {
            return consoleConfig.getOut();
        } else {
            return consoleConfig.getErrorOut();
        }
    }

    public static String getRedTag(String tag) {
        return Ansi.colorize("[" + tag + "]", redText);
    }

    public static String getRedText(String text) {
        return Ansi.colorize(text, redText);
    }

    public static String getGreenTag(String tag) {
        return Ansi.colorize("[" + tag + "]", greenText);
    }

    public static String getGreenText(String text) {
        return Ansi.colorize(text, greenText);
    }

    public static String getBlueTag(String tag) {
        return Ansi.colorize("[" + tag + "]", blueText);
    }

    public static String getBlueText(String text) {
        return Ansi.colorize(text, blueText);
    }

//    public static void printRedTag(PrintStream printStream, String tag) {
//        printStream.print(Ansi.colorize("[" + tag + "]", redText));
//    }
//
//    public static void printRedText(PrintStream printStream, String text) {
//        printStream.print(Ansi.colorize(text, redText));
//    }
//
//    public static void printGreenTag(PrintStream printStream, String tag) {
//        printStream.print(Ansi.colorize("[" + tag + "]", greenText));
//    }
//
//    public static void printGreenText(PrintStream printStream, String text) {
//        printStream.print(Ansi.colorize(text, greenText));
//    }
//
//    public static void printBlueTag(PrintStream printStream, String tag) {
//        printStream.print(Ansi.colorize("[" + tag + "]", blueText));
//    }
//
//    public static void printBlueText(PrintStream printStream, String text) {
//        printStream.print(Ansi.colorize(text, blueText));
//    }

}
