package edu.codespring.bibliospring.backend.service;

import edu.codespring.bibliospring.backend.controller.BookRequest;
import java.util.List;

public interface BookRequestServiceInterface {
    List<BookRequest> retrieveAllBooks();

    void createBookAndAuthor(BookRequest var1);
}
