package model;

public interface IRuleDomain {
    String getName();
    String getDescription();

    boolean update(IRuleDomain updateData);
}
