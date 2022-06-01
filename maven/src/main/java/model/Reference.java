package model;

import java.io.Serializable;

public class Reference implements IReference, Serializable {
    private ISource source;
    private IRuleDomain domain;

    public Reference(ISource source, IRuleDomain domain) {
        this.source = source;
        this.domain = domain;
    }

    public void setSource(ISource source) {
        this.source = source;
    }

    public void setDomain(IRuleDomain domain) {
        this.domain = domain;
    }

    @Override
    public ISource getSource() {
        return source;
    }

    @Override
    public IRuleDomain getDomain() {
        return domain;
    }
}
