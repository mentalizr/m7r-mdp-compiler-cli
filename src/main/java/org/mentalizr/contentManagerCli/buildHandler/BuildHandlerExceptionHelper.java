package org.mentalizr.contentManagerCli.buildHandler;

import org.mentalizr.contentManager.buildHandler.BuildHandlerException;
import org.mentalizr.mdpCompiler.MDPSyntaxError;

public class BuildHandlerExceptionHelper {

    public static boolean hasMdpSyntaxErrorAsCause(BuildHandlerException buildHandlerException) {
        return buildHandlerException.getCause() instanceof MDPSyntaxError;
    }

    public static MDPSyntaxError getCauseAsMDPSyntaxError(BuildHandlerException buildHandlerException) {
        if (!hasMdpSyntaxErrorAsCause(buildHandlerException))
            throw new IllegalArgumentException(
                    "Specified " + BuildHandlerException.class.getSimpleName()
                            + " has no " + MDPSyntaxError.class.getSimpleName() + " as cause."
            );

        return (MDPSyntaxError) buildHandlerException.getCause();
    }

}
