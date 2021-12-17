package org.mentalizr.contentManagerCli.console.outputFormatter;

import org.mentalizr.contentManagerCli.console.ConsoleConfig;
import org.mentalizr.contentManagerCli.console.OutputFormatterNew;

import java.util.List;

public class WarnOutputFormatter extends OutputFormatterNew {

    public WarnOutputFormatter(ConsoleConfig consoleConfig) {
        super(consoleConfig);
    }

    public void out(String message) {
        super.out(Destination.ERROR, message);
    }

    @Override
    protected String getPlainString(String message, List<String> messageParameters) {
        return "[Warn] " + message;
    }

    @Override
    protected String getColorizedString(String message, List<String> messageParameters) {
        return getYellowTag("Warn") + " " + message;
    }

}
