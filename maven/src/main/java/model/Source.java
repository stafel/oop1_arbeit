package model;

import java.time.LocalDate;

public class Source implements ISource {
    private String name;
    private String description;
    private String author;
    private LocalDate publishDate;

    public String getYear() {
        return Integer.toString(publishDate.getYear());
    }

    public void setYear(String year) {
        this.publishDate = LocalDate.of(Integer.parseInt(year), 1, 1);
    }

    public void setPublishDate(LocalDate date) {
        this.publishDate = date;
    }

    public LocalDate getPublishDate() {
        return this.publishDate;
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

    public Source(String name, String desctiption, String author, LocalDate publishDate) {
        this.description = desctiption;
        this.name = name;
        this.author = author;
        this.publishDate = publishDate;
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
        this.setPublishDate(updateData.getPublishDate());
        return true;
    }
}
