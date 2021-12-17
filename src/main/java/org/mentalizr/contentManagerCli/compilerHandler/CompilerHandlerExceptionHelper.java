package org.mentalizr.contentManagerCli.compilerHandler;

import org.mentalizr.mdpCompiler.MDPSyntaxError;

public class CompilerHandlerExceptionHelper {

    public static boolean hasMdpSyntaxErrorAsCause(CompilerHandlerException compilerHandlerException) {
        return compilerHandlerException.getCause() instanceof MDPSyntaxError;
    }

    public static MDPSyntaxError getCauseAsMDPSyntaxError(CompilerHandlerException compilerHandlerException) {
        if (!hasMdpSyntaxErrorAsCause(compilerHandlerException))
            throw new IllegalArgumentException(
                    "Specified " + CompilerHandlerException.class.getSimpleName()
                            + " has no " + MDPSyntaxError.class.getSimpleName() + " as cause."
            );

        return (MDPSyntaxError) compilerHandlerException.getCause();
    }

}
