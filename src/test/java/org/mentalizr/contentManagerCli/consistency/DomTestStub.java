package org.mentalizr.contentManagerCli.consistency;

import de.arthurpicht.utils.core.collection.Sets;
import org.mentalizr.mdpCompiler.Dom;

import java.util.Set;

public class DomTestStub extends DomAbstractTestStub implements Dom {

    @Override
    public Set<String> getReferencedMediaResources() {
        return Sets.newHashSet("A", "B", "C");
    }

}
