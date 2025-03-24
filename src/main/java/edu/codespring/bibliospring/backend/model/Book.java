package edu.codespring.bibliospring.backend.model;

public class Book extends BaseEntity {
    private String title;
    private String isbn;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
