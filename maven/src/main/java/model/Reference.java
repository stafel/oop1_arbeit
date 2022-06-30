package model;

import java.io.Serializable;

public class Reference implements IReference, Serializable {
    private String name;
    private String page;

    private ISource source;
    private IRuleDomain domain;

    public Reference(String name, ISource source, IRuleDomain domain, String page) {
        this.name = name;
        this.source = source;
        this.domain = domain;
        this.page = page;
    }

    public void setSource(ISource source) {
        this.source = source;
    }

    public void setDomain(IRuleDomain domain) {
        this.domain = domain;
    }

    @Override
    public ISource getSourceRef() {
        return source;
    }

    @Override
    public IRuleDomain getDomainRef() {
        return domain;
    }

    @Override
    public String getSource() {
        return source.getName();
    }

    @Override
    public String getDomain() {
        return domain.getName();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPage() {
        return page;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
