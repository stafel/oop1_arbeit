package model;

import java.time.LocalDate;

public class SourceBook extends Source {

    private String isbn;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public SourceBook(String name, String description, String author, LocalDate publishDate, String isbn) {
        super(name, description, author, publishDate);
        this.isbn = isbn;
    }

    public SourceBook() {
        super("", "", "", LocalDate.now());
        setIsbn("");
    }
    
    @Override
    public boolean update(ISource updateData) {
        if (updateData instanceof SourceBook) {
            SourceBook updateBook = (SourceBook)updateData;
            this.setIsbn(updateBook.getIsbn());
        }
        return super.update(updateData);
    }
}
