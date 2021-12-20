package org.mentalizr.contentManagerCli.executors.media.ls;

import de.arthurpicht.cli.CliCall;
import de.arthurpicht.cli.option.OptionParserResult;
import org.mentalizr.contentManager.exceptions.InconsistencyException;
import org.mentalizr.contentManagerCli.ContentManagerCli;
import org.mentalizr.contentManagerCli.executors.ExecutionContext;

public class MediaListExecutionContext extends ExecutionContext {

    private final boolean absolute;
    private final boolean showOrphanedOnly;

    public MediaListExecutionContext(CliCall cliCall) throws InconsistencyException {
        super(cliCall);
        OptionParserResult optionParserResultSpecific = cliCall.getOptionParserResultSpecific();
        this.absolute = optionParserResultSpecific.hasOption(ContentManagerCli.OPTION_MEDIA_ABSOLUTE);
        this.showOrphanedOnly = optionParserResultSpecific.hasOption(ContentManagerCli.OPTION_MEDIA_ORPHANED);
    }

    public boolean isAbsolute() {
        return absolute;
    }

    public boolean isShowOrphanedOnly() {
        return showOrphanedOnly;
    }

}
