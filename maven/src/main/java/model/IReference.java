package model;

/* reference is a quotation to a source (like a book or website) categorized into a domain */
public interface IReference {
    String getName();
    String getSource();
    String getDomain();
    String getPage();

    ISource getSourceRef();
    IRuleDomain getDomainRef();
}
