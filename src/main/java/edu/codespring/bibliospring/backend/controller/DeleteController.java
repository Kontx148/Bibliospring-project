//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.codespring.bibliospring.backend.controller;

import edu.codespring.bibliospring.backend.service.AuthorServiceInterface;
import edu.codespring.bibliospring.backend.service.BookServiceInterface;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class DeleteController extends HttpServlet {
    @Autowired
    private BookServiceInterface bookService;

    @Autowired
    private AuthorServiceInterface authorService;

    @DeleteMapping("Bibliospring/api/modify")
    public ResponseEntity<Void> deleteBookOrAuthor(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author) {
        if (title != null) {
            bookService.delete(title);
        } else if (author != null) {
            authorService.delete(author);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok().build();
    }
}
