package org.mentalizr.contentManagerCli.executors;

import de.arthurpicht.cli.CommandExecutor;
import org.mentalizr.contentManager.Program;
import org.mentalizr.contentManager.exceptions.ContentManagerException;
import org.mentalizr.contentManager.programStructure.ProgramStructure;
import org.mentalizr.contentManagerCli.ExecutionContext;
import org.mentalizr.contentManagerCli.ProgramPath;
import org.mentalizr.contentManagerCli.console.Console;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;

public class ShowStructureExecutor extends AbstractExecutor implements CommandExecutor {

    @Override
    protected String getOperationName() {
        return "show structure";
    }

    @Override
    protected String getMessageTextSuccess() {
        return "Program structure shown successfully.";
    }

    @Override
    protected String getMessageTextFailed() {
        return "Error showing program structure.";
    }

    @Override
    protected boolean processProgram(ExecutionContext executionContext, ProgramPath programPath) {
        try {
            Program program = new Program(programPath.getPath());
            if (program.hasHtmlDir()) {
                ProgramStructure programStructure = program.asProgramStructure();

                Jsonb jsonb = JsonbBuilder.create(new JsonbConfig().withFormatting(true));
                System.out.println(jsonb.toJson(programStructure));

                return true;
            } else {
                Console.errorProgramOut(program.getName(), "Program not built yet.");
                return false;
            }
        } catch (ContentManagerException e) {
            Console.errorProgramOut(programPath.getProgramName(), e.getMessage());
            if (executionContext.isStacktrace()) e.printStackTrace();
            return false;
        }
    }

}
