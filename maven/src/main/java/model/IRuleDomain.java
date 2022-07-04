package model;

/* RuleDomain or short domain is a grouping for references */
public interface IRuleDomain {
    String getName();
    String getDescription();

    boolean update(IRuleDomain updateData);
}
