package model;

/* reference is a quotation to a source (like a book or website) categorized into a domain */
public interface IReference {
    ISource getSource();
    IRuleDomain getDomain();
    String getListText();
}
