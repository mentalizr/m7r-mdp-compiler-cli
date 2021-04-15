package org.mentalizr.contentManagerCli;

import de.arthurpicht.cli.*;
import de.arthurpicht.cli.command.CommandSequenceBuilder;
import de.arthurpicht.cli.command.Commands;
import de.arthurpicht.cli.command.InfoDefaultCommand;
import de.arthurpicht.cli.common.UnrecognizedArgumentException;
import de.arthurpicht.cli.option.ManOption;
import de.arthurpicht.cli.option.OptionBuilder;
import de.arthurpicht.cli.option.Options;
import de.arthurpicht.cli.option.VersionOption;
import de.arthurpicht.cli.parameter.ParametersMin;
import de.arthurpicht.cli.parameter.ParametersOne;
import org.mentalizr.contentManager.exceptions.ConsistencyException;
import org.mentalizr.contentManagerCli.console.*;
import org.mentalizr.contentManagerCli.executors.*;

public class ContentManagerCli {

    public static final String OPTION_VERBOSE = "verbose";
    public static final String OPTION_STACKTRACE = "stacktrace";
    public static final String OPTION_CONTENT_ROOT = "content_root";
    public static final String OPTION_SILENT = "silent";
    public static final String OPTION_LOGGER = "logger";
    public static final String OPTION_LOGGER_NAME = "logger_name";
    public static final String OPTION_NO_COLOR = "no_color";

    public static final String OPTION__CLEAN__FORCE = "clean__force";

    private static Cli createCli() {

        Options globalOptions = new Options()
                .add(new VersionOption())
                .add(new ManOption())
                .add(new OptionBuilder().withLongName("verbose").withDescription("verbose output").build(OPTION_VERBOSE))
                .add(new OptionBuilder().withShortName('p').withLongName("content-root").withArgumentName("path").withDescription("Path to content root directory.").build(OPTION_CONTENT_ROOT))
                .add(new OptionBuilder().withShortName('s').withLongName("stacktrace").withDescription("Show stacktrace when running on error.").build(OPTION_STACKTRACE))
                .add(new OptionBuilder().withLongName("silent").withDescription("Make no output to console.").build(OPTION_SILENT))
                .add(new OptionBuilder().withLongName("no-color").withDescription("Ommit colorization on console output.").build(OPTION_NO_COLOR))
                .add(new OptionBuilder().withShortName('l').withLongName("logger").withDescription("Print output to logger.").build(OPTION_LOGGER))
                .add(new OptionBuilder().withLongName("logger-name").withDescription("Name of logger. Default is 'org.mentalizr.contentManagerCli'.").build(OPTION_LOGGER_NAME));

        Commands commands = new Commands();

        commands.setDefaultCommand(new InfoDefaultCommand());

        commands.add(new CommandSequenceBuilder()
                .addCommands("build")
                .withParameters(new ParametersMin(0, "program", "programs to be built"))
                .withCommandExecutor(new BuildExecutor())
                .withDescription("Executes a build on specified programs or on all programs if none is specified.")
                .build()
        );

        commands.add(new CommandSequenceBuilder()
                .addCommands("clean")
//                .withSpecificOptions(new Options()
//                        .add(new OptionBuilder().withShortName('f').withLongName("force").withDescription("force cleaning program directory").build(OPTION__CLEAN__FORCE)))
                .withParameters(new ParametersMin(0, "program", "programs to be cleaned"))
                .withCommandExecutor(new CleanExecutor())
                .withDescription("Cleans specified programs or all programs if none is specified.")
                .build()
        );

        commands.add(new CommandSequenceBuilder()
                .addCommands("show")
                .withParameters(new ParametersMin(0, "program", "programs to be built"))
                .withCommandExecutor(new ShowExecutor())
                .withDescription("Shows program structure for specified programs or for all programs if none is specified.")
                .build()
        );

        commands.add(new CommandSequenceBuilder()
                .addCommands("check")
                .withParameters(new ParametersOne("content-id", "content id of mdp file to be checked"))
                .withCommandExecutor(new CheckExecutor())
                .withDescription("Checks a single mdp file for syntactically correctness. With no impact for the repo state. For development purposes.")
                .build()
        );

        CliDescription cliDescription = new CliDescriptionBuilder()
                .withDescription("mentalizr content manager CLI\nhttps://github.com/mentalizr/m7r-content-manager-cli")
                .withVersion("0.1-SNAPSHOT")
                .withDate("2021-03-21")
                .build("m7r-cm");

        return new CliBuilder()
                .withGlobalOptions(globalOptions)
                .withCommands(commands)
                .withAutoHelp()
                .build(cliDescription);

    }

    public static void main(String[] args) {
        Cli cli = createCli();
        CliCall cliCall = null;
        try {
            cliCall = cli.parse(args);
        } catch (UnrecognizedArgumentException e) {
            System.out.println(e.getExecutableName() + " call syntax error. " + e.getMessage());
            System.out.println(e.getCallString());
            System.out.println(e.getCallPointerString());
            System.exit(1);
        }

        boolean showStacktrace = cliCall.getOptionParserResultGlobal().hasOption(OPTION_STACKTRACE);

        ConsoleConfig consoleConfig = ConsoleConfigCreator.create(cliCall);
        Console.initialize(consoleConfig);
        String welcomeString = cliCall.getCliDefinition().getCliDescription().getDescriptionFirstLine()
                + " - Version " + cliCall.getCliDefinition().getCliDescription().getVersion();
        Console.out(welcomeString);

        try {
            cli.execute(cliCall);
        } catch (CommandExecutorException e) {
            if (e.getCause() != null && e.getCause() instanceof ConsistencyException) {
                Console.errorOut(e.getMessage());
                if (showStacktrace) e.printStackTrace(consoleConfig.getErrorOut());
//            } else {
//                System.out.println("CommandExecutorException: " + e.getMessage() );
//                e.printStackTrace();
            }
            System.exit(1);
        } catch (RuntimeException | AssertionError e) {
            Console.internalErrorOut(e.getMessage());
            if (showStacktrace) e.printStackTrace(consoleConfig.getErrorOut());
            System.exit(1);
        }
    }

}
