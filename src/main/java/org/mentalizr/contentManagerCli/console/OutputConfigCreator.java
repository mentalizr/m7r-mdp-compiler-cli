package org.mentalizr.contentManagerCli.console;

import de.arthurpicht.cli.CliCall;
import de.arthurpicht.cli.option.OptionParserResult;
import org.mentalizr.contentManagerCli.ContentManagerCli;

public class OutputConfigCreator {

    public static OutputConfig create(CliCall cliCall) {

        OptionParserResult optionParserResult = cliCall.getOptionParserResultGlobal();

        OutputConfigBuilder outputConfigBuilder = new OutputConfigBuilder();

        if (optionParserResult.hasOption(ContentManagerCli.OPTION_SILENT))
            outputConfigBuilder.withOutputToConsole(false);

        if (optionParserResult.hasOption(ContentManagerCli.OPTION_NO_COLOR))
            outputConfigBuilder.withColorizedConsole(false);

        if (optionParserResult.hasOption(ContentManagerCli.OPTION_LOGGER))
            outputConfigBuilder.withOutputToLogger(true);

        if (optionParserResult.hasOption(ContentManagerCli.OPTION_LOGGER_NAME)) {
            String loggerName = optionParserResult.getValue(ContentManagerCli.OPTION_LOGGER_NAME);
            outputConfigBuilder.withOutputToLogger(true);
            outputConfigBuilder.withLoggerName(loggerName);
        }

        return outputConfigBuilder.build();
    }

}
