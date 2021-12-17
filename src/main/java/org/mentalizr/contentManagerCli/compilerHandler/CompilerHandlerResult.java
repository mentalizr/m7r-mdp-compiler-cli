package org.mentalizr.contentManagerCli.compilerHandler;

import java.util.List;
import java.util.Set;

public class CompilerHandlerResult {

    private final List<String> html;
    private final Set<String> mediaResources;

    public CompilerHandlerResult(List<String> html, Set<String> mediaResources) {
        this.html = html;
        this.mediaResources = mediaResources;
    }

    public List<String> getHtml() {
        return html;
    }

    public Set<String> getMediaResources() {
        return mediaResources;
    }

}
