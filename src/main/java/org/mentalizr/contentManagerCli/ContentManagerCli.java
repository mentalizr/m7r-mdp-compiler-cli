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
import org.mentalizr.contentManagerCli.executors.*;

public class ContentManagerCli {

    public static final String OPTION_VERBOSE = "verbose";
    public static final String OPTION_STACKTRACE = "stacktrace";
    public static final String OPTION_CONTENT_ROOT = "content_root";

    private static Cli createCli() {

        // # mdpc clean build

        Options globalOptions = new Options()
                .add(new VersionOption())
                .add(new ManOption())
                .add(new OptionBuilder().withLongName("verbose").withDescription("verbose output").build(OPTION_VERBOSE))
                .add(new OptionBuilder().withShortName('p').withLongName("content-root").withArgumentName("path").withDescription("Path to content root directory.").build(OPTION_CONTENT_ROOT))
                .add(new OptionBuilder().withShortName('s').withLongName("stacktrace").withDescription("Show stacktrace when running on error.").build(OPTION_STACKTRACE));

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
                .addCommands("clean", "build")
                .withParameters(new ParametersMin(0, "program", "programs to be built"))
                .withCommandExecutor(new CleanBuildExecutor())
                .withDescription("Executes a clean build on specified programs or on all programs if none is specified.")
                .build()
        );

        commands.add(new CommandSequenceBuilder()
                .addCommands("clean")
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
                .addCommands("compile")
                .withParameters(new ParametersOne("content-id", "content id of mdp file to be compiled"))
                .withCommandExecutor(new CompileExecutor())
                .withDescription("Compiles a single mdp file with no impact for the repo state and for development purposes only.")
                .build()
        );

        CliDescription cliDescription = new CliDescriptionBuilder()
                .withDescription("The mentalizr content manager CLI.\nhttps://github.com/mentalizr/m7r-content-manager-cli")
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

        try {
            cli.execute(cliCall);
        } catch (CommandExecutorException e) {
            System.out.println(cliCall.getCliDefinition().getCliDescription().getExecutableName() + " command execution error. " + e.getMessage());
            if (showStacktrace) e.printStackTrace();
            System.exit(1);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            if (showStacktrace) e.printStackTrace();
            System.exit(1);
        }



    }

}
