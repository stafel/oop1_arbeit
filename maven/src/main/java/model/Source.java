package model;

import java.io.Serializable;

public class Source implements ISource, Serializable {
    private String name;
    private String description;
    private String author;
    private String year;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Source(String name, String desctiption, String author, String year) {
        this.description = desctiption;
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    
    public boolean update(ISource updateData) {
        this.setDescription(updateData.getDescription());
        this.setAuthor(updateData.getAuthor());
        this.setYear(updateData.getYear());
        return true;
    }
}
