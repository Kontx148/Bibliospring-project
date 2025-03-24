package edu.codespring.bibliospring.backend.repository.jdbc;

import edu.codespring.bibliospring.backend.model.Author;
import edu.codespring.bibliospring.backend.repository.AuthorRepository;
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
public class JdbcAuthorRepository implements AuthorRepository {
    @Autowired
    private ConnectionManager connectionManager;

    @Autowired
    private Logger LOG;


    public Author create(Author author) {
        Connection connection = this.connectionManager.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO AUTHOR (UID,authorName) VALUES (?,?);", 1);
            ps.setString(1, author.getUuid());
            ps.setString(2, author.getName());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            author.setId(rs.getLong(1));
        } catch (SQLException e) {
            LOG.error("Author creation failed", e);
            throw new RepositoryException("Author creation failed", e);
        } finally {
            if (connection != null) {
                this.connectionManager.returnConnection(connection);
            }

        }

        return author;
    }

    public Author getByID(long id) {
        Connection connection = this.connectionManager.getConnection();

        Author author;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM AUTHOR WHERE ID = ?;");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            author = new Author();
            author.setUuid(rs.getString("UID"));
            author.setId(rs.getLong("ID"));
            author.setName(rs.getString("authorName"));
        } catch (SQLException e) {
            LOG.error("Getting Author by ID failed", e);
            throw new RepositoryException("Getting Author by ID failed", e);
        } finally {
            if (connection != null) {
                this.connectionManager.returnConnection(connection);
            }

        }

        return null;
    }

    public void update(Author author) {
        Connection connection = this.connectionManager.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO AUTHOR (UID,authorName) VALUES (?,?);");
            ps.setString(1, author.getUuid());
            ps.setString(2, author.getName());
            ps.execute();
        } catch (SQLException e) {
            LOG.error("Updating Author failed", e);
            throw new RepositoryException("Updating Author failed", e);
        } finally {
            if (connection != null) {
                this.connectionManager.returnConnection(connection);
            }

        }

    }

    public void delete(Author author) {
        Connection connection = this.connectionManager.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM AUTHOR WHERE UID=?;");
            ps.setString(1, author.getUuid());
            if (ps.executeUpdate() == 0) {
                LOG.info("No rows found to delete author");
            }
        } catch (SQLException e) {
            LOG.error("Deleting Author failed", e);
            throw new RepositoryException("Deleting author failed", e);
        } finally {
            if (connection != null) {
                this.connectionManager.returnConnection(connection);
            }

        }

    }

    public List<Author> getAll() {
        return null;
    }

    public Author getByName(String name) {
        Connection connection = this.connectionManager.getConnection();

        Author author;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM AUTHOR WHERE authorName = ?;");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (!rs.isBeforeFirst()) {
                return null;
            }

            rs.next();
            author = new Author();
            author.setUuid(rs.getString("UID"));
            author.setId(rs.getLong("ID"));
            author.setName(rs.getString("authorName"));
        } catch (SQLException var10) {
            LOG.error("Getting Author by Name failed", var10);
            throw new RepositoryException("Getting Author by Name failed", var10);
        } finally {
            if (connection != null) {
                this.connectionManager.returnConnection(connection);
            }

        }

        return author;
    }
}