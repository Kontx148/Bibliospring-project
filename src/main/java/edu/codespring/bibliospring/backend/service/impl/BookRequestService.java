package edu.codespring.bibliospring.backend.service.impl;

import edu.codespring.bibliospring.backend.controller.BookRequest;
import edu.codespring.bibliospring.backend.model.Author;
import edu.codespring.bibliospring.backend.model.Book;
import edu.codespring.bibliospring.backend.repository.IntermediateRepository;
import edu.codespring.bibliospring.backend.repository.RepositoryException;
import edu.codespring.bibliospring.backend.service.AuthorServiceInterface;
import edu.codespring.bibliospring.backend.service.BookRequestServiceInterface;
import edu.codespring.bibliospring.backend.service.BookServiceInterface;
import edu.codespring.bibliospring.backend.service.ServiceException;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookRequestService implements BookRequestServiceInterface {
    @Autowired
    private IntermediateRepository intermediateRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private Logger LOG;

    public List<BookRequest> retrieveAllBooks() {
        try {
            List<BookRequest> books = this.intermediateRepository.getAllBooks();
            LOG.info("BookRequests delivered successfully");
            return books;
        } catch (RepositoryException var3) {
            LOG.error("Error delivering Book requests");
            throw new ServiceException("Error delivering Book requests");
        }
    }

    public void createBookAndAuthor(BookRequest bookRequest) {
        Book book = new Book();
        book.setTitle(bookRequest.getTitle());
        book.setIsbn(bookRequest.getIsbn());
        Book registeredBook = bookService.register(book);
        Author author = new Author();
        author.setName((String) bookRequest.getAuthors().getFirst());
        Author registeredAuthor = authorService.register(author);
        if (!book.equals(registeredBook) && !author.equals(registeredAuthor)) {
            List<Author> authors = this.intermediateRepository.getAuthors(registeredBook);
            if (authors.contains(registeredAuthor)) {
                throw new ServiceException("Book and author already exists");
            }
        }

        this.intermediateRepository.addAuthorBook(registeredAuthor, registeredBook);
    }
}
