package org.mentalizr.mdpCompilerCli;

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

public class MdpCompilerCli {

    public static final String OPTION_STACKTRACE = "stacktrace";
    public static final String OPTION_CONTENT_ROOT = "content_root";

    private static Cli createCli() {

        // # mdpc clean build

        Options globalOptions = new Options()
                .add(new VersionOption())
                .add(new ManOption())
                .add(new OptionBuilder().withShortName('p').withLongName("content-root").withArgumentName("path").withDescription("Path to content root directory.").build(OPTION_CONTENT_ROOT))
                .add(new OptionBuilder().withShortName('s').withLongName("stacktrace").withDescription("Show stacktrace when running on error.").build(OPTION_STACKTRACE));

        Commands commands = new Commands();

        commands.setDefaultCommand(new InfoDefaultCommand());

        commands.add(new CommandSequenceBuilder()
                .addCommands("clean", "build")
                .withParameters(new ParametersMin(0, "program", "programs to be built"))
                .withCommandExecutor(new CleanBuildExecutor())
                .withDescription("Executes a clean build on specified programs or on all programs if none is specified.")
                .build()
        );

        CliDescription cliDescription = new CliDescriptionBuilder()
                .withDescription("The mdp compiler.\nhttps://github.com/mentalizr/m7r-mdp-compiler-cli")
                .withVersion("0.1-SNAPSHOT")
                .withDate("2021-03-20")
                .build("mdpc2");

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
            System.out.println("mdpc call syntax error. " + e.getMessage());
            System.out.println(e.getCallString());
            System.out.println(e.getCallPointerString());
            System.exit(1);
        }

        boolean showStacktrace = cliCall.getOptionParserResultGlobal().hasOption(OPTION_STACKTRACE);

        try {
            cli.execute(cliCall);
        } catch (CommandExecutorException e) {
            System.out.println("mdpc command execution error. " + e.getMessage());
            if (showStacktrace) e.printStackTrace();
            System.exit(1);
        }



    }

}
