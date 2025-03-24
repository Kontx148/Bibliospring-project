package edu.codespring.bibliospring.backend.service;

import edu.codespring.bibliospring.backend.model.Book;

public interface BookServiceInterface {
    Book register(Book var1);

    void delete(String var1);
}
