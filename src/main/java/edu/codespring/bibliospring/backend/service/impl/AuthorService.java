package edu.codespring.bibliospring.backend.service.impl;

import edu.codespring.bibliospring.backend.model.Author;
import edu.codespring.bibliospring.backend.repository.AuthorRepository;
import edu.codespring.bibliospring.backend.repository.RepositoryException;
import edu.codespring.bibliospring.backend.service.AuthorServiceInterface;
import edu.codespring.bibliospring.backend.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService implements AuthorServiceInterface {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private Logger LOG;

    public Author register(Author author) {
        try {
            Author lookedUpAuthor = this.authorRepository.getByName(author.getName());
            if (lookedUpAuthor != null && author.getName().equals(lookedUpAuthor.getName())) {
                LOG.info("Author already exists");
                return lookedUpAuthor;
            } else {
                LOG.info("Author created successfully");
                return this.authorRepository.create(author);
            }
        } catch (RepositoryException var3) {
            LOG.error("Error registering Author");
            throw new ServiceException("Error registering Author");
        }
    }

    public void delete(String name) {
        try {
            Author authorToDelete = this.authorRepository.getByName(name);
            this.authorRepository.delete(authorToDelete);
            LOG.info("Author deleted successfully");
        } catch (RepositoryException var3) {
            LOG.error("Error deleting Author");
            throw new ServiceException("Error deleting Author");
        }
    }
}