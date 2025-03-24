package edu.codespring.bibliospring.backend.service.impl;

import edu.codespring.bibliospring.backend.model.Book;
import edu.codespring.bibliospring.backend.repository.BookRepository;
import edu.codespring.bibliospring.backend.repository.RepositoryException;
import edu.codespring.bibliospring.backend.service.BookServiceInterface;
import edu.codespring.bibliospring.backend.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService implements BookServiceInterface {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private Logger LOG;

    public Book register(Book book) {
        try {
            Book lookedUpBook = this.bookRepository.getByTitle(book.getTitle());
            if (lookedUpBook != null && book.getIsbn().equals(lookedUpBook.getIsbn())) {
                LOG.info("Book already exists");
                return lookedUpBook;
            } else {
                lookedUpBook = this.bookRepository.getByISBN(book.getIsbn());
                if (lookedUpBook != null) {
                    LOG.info("Book with the same ISBN already exists");
                    throw new ServiceException("ISBN taken");
                } else {
                    LOG.info("Book created");
                    return this.bookRepository.create(book);
                }
            }
        } catch (RepositoryException var3) {
            LOG.error("Error registering Book");
            throw new ServiceException("Error registering Book");
        }
    }

    public void delete(String title) {
        try {
            Book bookToDelete = this.bookRepository.getByTitle(title);
            this.bookRepository.delete(bookToDelete);
            LOG.info("Book deleted successfully");
        } catch (RepositoryException var3) {
            LOG.error("Error deleting Book");
            throw new ServiceException("Error deleting Book");
        }
    }
}
