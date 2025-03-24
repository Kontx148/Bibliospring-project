package edu.codespring.bibliospring.backend.controller;

import com.google.gson.Gson;
import edu.codespring.bibliospring.backend.service.BookRequestServiceInterface;
import edu.codespring.bibliospring.backend.service.ServiceException;
import edu.codespring.bibliospring.backend.service.impl.BookRequestService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController extends HttpServlet {
    @Autowired
    private BookRequestServiceInterface bookRequestService;

    @Autowired
    private Logger LOG;

    @PostMapping("Bibliospring/api/books")
    public ResponseEntity<String> addBook(@RequestBody BookRequest bookRequest) {
        try {
            this.bookRequestService.createBookAndAuthor(bookRequest);
            LOG.info("Book and author created successfully");
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Book and author created successfully");
        } catch (Exception e) {
            LOG.error("Error creating book and author");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create book : " + e.getMessage());
        }
    }

    @GetMapping("Bibliospring/api/books")
    public ResponseEntity<List<BookRequest>> getAllBooks() {
        try {
            List<BookRequest> books = bookRequestService.retrieveAllBooks();
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .body(books);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

}
