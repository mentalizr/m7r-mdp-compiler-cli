package org.mentalizr.contentManagerCli.console;

import de.arthurpicht.cli.CliCall;
import de.arthurpicht.cli.option.OptionParserResult;
import org.mentalizr.contentManagerCli.ContentManagerCli;

public class ConsoleConfigCreator {

    public static ConsoleConfig create(CliCall cliCall) {

        OptionParserResult optionParserResult = cliCall.getOptionParserResultGlobal();

        ConsoleConfigBuilder consoleConfigBuilder = new ConsoleConfigBuilder();

        if (optionParserResult.hasOption(ContentManagerCli.OPTION_SILENT))
            consoleConfigBuilder.withOutputToConsole(false);

        if (optionParserResult.hasOption(ContentManagerCli.OPTION_NO_COLOR))
            consoleConfigBuilder.withColorizedConsole(false);

        if (optionParserResult.hasOption(ContentManagerCli.OPTION_LOGGER))
            consoleConfigBuilder.withOutputToLogger(true);

        if (optionParserResult.hasOption(ContentManagerCli.OPTION_LOGGER_NAME)) {
            String loggerName = optionParserResult.getValue(ContentManagerCli.OPTION_LOGGER_NAME);
            consoleConfigBuilder.withOutputToLogger(true);
            consoleConfigBuilder.withLoggerName(loggerName);
        }

        return consoleConfigBuilder.build();
    }

}
