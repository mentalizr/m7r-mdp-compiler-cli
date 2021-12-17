package org.mentalizr.contentManagerCli.consistency;

import org.mentalizr.mdpCompiler.Dom;
import org.mentalizr.mdpCompiler.outlineElement.OutlineElementModel;

import java.util.List;
import java.util.Set;

public abstract class DomAbstractTestStub implements Dom {

    private static final String message = "Test stub method. Intentionally not implemented.";

    @Override
    public void addOutlineElementModel(OutlineElementModel outlineElementModel) {
        throw new RuntimeException(message);
    }

    @Override
    public List<OutlineElementModel> getOutlineElementModels() {
        throw new RuntimeException(message);
    }

    @Override
    public Set<String> getReferencedMediaResources() {
        throw new RuntimeException(message);
    }

}
