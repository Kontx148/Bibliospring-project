package edu.codespring.bibliospring.backend.repository.jdbc;

import edu.codespring.bibliospring.backend.controller.BookRequest;
import edu.codespring.bibliospring.backend.model.Author;
import edu.codespring.bibliospring.backend.model.Book;
import edu.codespring.bibliospring.backend.repository.ConnectionManager;
import edu.codespring.bibliospring.backend.repository.IntermediateRepository;
import edu.codespring.bibliospring.backend.repository.RepositoryException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("JDBC")
public class JdbcIntermediateRepository implements IntermediateRepository {
    @Autowired
    private ConnectionManager connectionManager;

    @Autowired
    private Logger LOG;

    public void addAuthorBook(Author author, Book book) {
        Connection connection = this.connectionManager.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO bookAuthor (AuthorID,BookID) VALUES (?,?);");
            ps.setLong(1, author.getId());
            ps.setLong(2, book.getId());
            ps.execute();
        } catch (SQLException e) {
            LOG.error("Adding Author-Book failed", e);
            throw new RepositoryException("Adding Author-Book failed", e);
        } finally {
            if (connection != null) {
                this.connectionManager.returnConnection(connection);
            }

        }

    }

    public List<Author> getAuthors(Book book) {
        Connection connection = this.connectionManager.getConnection();
        List<Author> authors = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT A.ID,A.UID,A.authorName FROM bookAuthor BA JOIN AUTHOR A ON BA.AuthorID = A.ID WHERE BA.BookID = ?;");
            ps.setLong(1, book.getId());
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Author author = new Author();
                author.setUuid(rs.getString("UID"));
                author.setId(rs.getLong("ID"));
                author.setName(rs.getString("authorName"));
                authors.add(author);
            }

            return authors;
        } catch (SQLException e) {
            LOG.error("Getting Authors failed", e);
            throw new RepositoryException("Getting Authors failed", e);
        } finally {
            if (connection != null) {
                this.connectionManager.returnConnection(connection);
            }

        }
    }

    public List<Book> getBooks(Author author) {
        Connection connection = this.connectionManager.getConnection();
        List<Book> books = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT B.ID, B.UID, B.title, B.isbn FROM bookAuthor BA JOIN BOOK B ON BA.BookID = B.ID WHERE BA.AuthorID = ?;");
            ps.setLong(1, author.getId());
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Book book = new Book();
                book.setUuid(rs.getString("UID"));
                book.setId(rs.getLong("ID"));
                book.setTitle(rs.getString("title"));
                book.setIsbn(rs.getString("isbn"));
                books.add(book);
            }

            return books;
        } catch (SQLException e) {
            LOG.error("Getting Authors failed", e);
            throw new RepositoryException("Getting Authors failed", e);
        } finally {
            if (connection != null) {
                this.connectionManager.returnConnection(connection);
            }

        }
    }

    public List<BookRequest> getAllBooks() {
        Connection connection = this.connectionManager.getConnection();
        List<BookRequest> books = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT GROUP_CONCAT(A.authorName ORDER BY A.authorName SEPARATOR ', ') AS authors, B.title, B.isbn FROM bookAuthor BA JOIN BOOK B ON BA.BookID = B.ID JOIN AUTHOR A ON A.ID = BA.AuthorID GROUP BY B.title, B.isbn ORDER BY B.title;");
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                BookRequest bookRequest = new BookRequest();
                bookRequest.setAuthors(Arrays.stream(rs.getString("authors").split(",")).toList());
                bookRequest.setIsbn(rs.getString("isbn"));
                bookRequest.setTitle(rs.getString("title"));
                books.add(bookRequest);
            }

            return books;
        } catch (SQLException e) {
            LOG.error("Getting all Books failed", e);
            throw new RepositoryException("Getting all Books failed", e);
        } finally {
            if (connection != null) {
                this.connectionManager.returnConnection(connection);
            }

        }
    }
}
