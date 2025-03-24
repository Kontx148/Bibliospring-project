package edu.codespring.bibliospring.backend.service;

import edu.codespring.bibliospring.backend.model.Author;

public interface AuthorServiceInterface {
    Author register(Author var1);

    void delete(String var1);
}
