package edu.codespring.bibliospring.backend.repository;

import edu.codespring.bibliospring.backend.model.Author;
import java.util.List;

public interface AuthorRepository {
    Author create(Author var1);

    Author getByID(long var1);

    void update(Author var1);

    void delete(Author var1);

    List<Author> getAll();

    Author getByName(String var1);
}
