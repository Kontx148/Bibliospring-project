package edu.codespring.bibliospring.backend.repository;

import edu.codespring.bibliospring.backend.controller.BookRequest;
import edu.codespring.bibliospring.backend.model.Author;
import edu.codespring.bibliospring.backend.model.Book;
import java.util.List;

public interface IntermediateRepository {
    void addAuthorBook(Author var1, Book var2);

    List<Author> getAuthors(Book var1);

    List<Book> getBooks(Author var1);

    List<BookRequest> getAllBooks();
}
