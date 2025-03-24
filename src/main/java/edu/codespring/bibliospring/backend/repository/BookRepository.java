package edu.codespring.bibliospring.backend.repository;

import edu.codespring.bibliospring.backend.model.Book;
import java.util.List;

public interface BookRepository {
    Book create(Book var1);

    Book getByID(long var1);

    void update(Book var1);

    void delete(Book var1);

    List<Book> getAll();

    Book getByTitle(String var1);

    Book getByISBN(String var1);
}
