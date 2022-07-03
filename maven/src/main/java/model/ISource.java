package model;

/* source holds general reusable data about a quotable media like a book */
public interface ISource {
    String getName();
    String getDescription();
    String getAuthor();
    String getYear();

    boolean update(ISource updateData); // update attributes from another source, returns false if source is unknown and can not be updated
}
