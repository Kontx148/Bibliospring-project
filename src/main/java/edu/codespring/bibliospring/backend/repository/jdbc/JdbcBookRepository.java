package edu.codespring.bibliospring.backend.repository.jdbc;

import edu.codespring.bibliospring.backend.model.Book;
import edu.codespring.bibliospring.backend.repository.BookRepository;
import edu.codespring.bibliospring.backend.repository.ConnectionManager;
import edu.codespring.bibliospring.backend.repository.RepositoryException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("JDBC")
public class JdbcBookRepository implements BookRepository {
    @Autowired
    private ConnectionManager connectionManager;

    @Autowired
    private Logger LOG;

    public Book create(Book book) {
        Connection connection = this.connectionManager.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO BOOK (UID,title,isbn) VALUES (?,?,?);", 1);
            ps.setString(1, book.getUuid());
            ps.setString(2, book.getTitle());
            ps.setString(3, book.getIsbn());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            book.setId(rs.getLong(1));
        } catch (SQLException e) {
            LOG.error("Book creation failed", e);
            throw new RepositoryException("Book creation failed", e);
        } finally {
            if (connection != null) {
                this.connectionManager.returnConnection(connection);
            }
        }

        return book;
    }

    public Book getByID(long id) {
        Connection connection = this.connectionManager.getConnection();

        Book bookByID;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM BOOK WHERE ID = ?;");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            bookByID = new Book();
            bookByID.setUuid(rs.getString("UID"));
            bookByID.setId(rs.getLong("ID"));
            bookByID.setTitle(rs.getString("title"));
            bookByID.setIsbn(rs.getString("isbn"));
        } catch (SQLException e) {
            LOG.error("Getting Book by ID failed", e);
            throw new RepositoryException("Getting Book by ID failed", e);
        } finally {
            if (connection != null) {
                this.connectionManager.returnConnection(connection);
            }

        }
        return bookByID;
    }

    public void update(Book book) {
        Connection connection = this.connectionManager.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO BOOK (UID,title,isbn) VALUES (?,?,?);");
            ps.setString(1, book.getUuid());
            ps.setString(2, book.getTitle());
            ps.setString(3, book.getIsbn());
            ps.execute();
        } catch (SQLException e) {
            LOG.error("Updating book failed", e);
            throw new RepositoryException("Updating book failed", e);
        } finally {
            if (connection != null) {
                this.connectionManager.returnConnection(connection);
            }

        }

    }

    public void delete(Book book) {
        Connection connection = this.connectionManager.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM BOOK WHERE UID=?;");
            ps.setString(1, book.getUuid());
            if (ps.executeUpdate() == 0) {
                LOG.info("No rows found to delete book");
            }
        } catch (SQLException e) {
            LOG.error("Deleting user failed", e);
            throw new RepositoryException("Deleting user failed", e);
        } finally {
            if (connection != null) {
                this.connectionManager.returnConnection(connection);
            }

        }

    }

    public List<Book> getAll() {
        return null;
    }

    public Book getByTitle(String title) {
        Connection connection = this.connectionManager.getConnection();

        Book book;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM BOOK WHERE title = ?;");
            ps.setString(1, title);
            ResultSet rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                rs.next();
                book = new Book();
                book.setUuid(rs.getString("UID"));
                book.setId(rs.getLong("ID"));
                book.setTitle(rs.getString("title"));
                book.setIsbn(rs.getString("isbn"));
                return book;
            }
        } catch (SQLException e) {
            LOG.error("Getting Book by Name failed", e);
            throw new RepositoryException("Getting Book by Name failed", e);
        } finally {
            if (connection != null) {
                this.connectionManager.returnConnection(connection);
            }

        }
        return null;
    }

    public Book getByISBN(String isbn) {
        Connection connection = this.connectionManager.getConnection();

        Book book;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM BOOK WHERE isbn = ?;");
            ps.setString(1, isbn);
            ResultSet rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                rs.next();
                book = new Book();
                book.setUuid(rs.getString("UID"));
                book.setId(rs.getLong("ID"));
                book.setTitle(rs.getString("title"));
                book.setIsbn(rs.getString("isbn"));
                return book;
            }

        } catch (SQLException e) {
            LOG.error("Getting Book by ISBN failed", e);
            throw new RepositoryException("Getting Book by ISBN failed", e);
        } finally {
            if (connection != null) {
                this.connectionManager.returnConnection(connection);
            }

        }

        return null;
    }
}
