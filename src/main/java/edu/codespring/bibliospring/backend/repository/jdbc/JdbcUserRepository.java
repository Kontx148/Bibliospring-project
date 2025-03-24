package edu.codespring.bibliospring.backend.repository.jdbc;

import edu.codespring.bibliospring.backend.model.User;
import edu.codespring.bibliospring.backend.repository.ConnectionManager;
import edu.codespring.bibliospring.backend.repository.RepositoryException;
import edu.codespring.bibliospring.backend.repository.UserRepository;
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
public class JdbcUserRepository implements UserRepository {
    @Autowired
    private ConnectionManager connectionManager;

    @Autowired
    private Logger LOG;

    public User create(User user) {
        Connection connection = this.connectionManager.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO USER (UID,username,password) VALUES (?,?,?);", 1);
            ps.setString(1, user.getUuid());
            ps.setString(2, user.getUserName());
            ps.setString(3, user.getPassword());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            user.setId(rs.getLong(1));
        } catch (SQLException e) {
            LOG.error("User creation failed", e);
            throw new RepositoryException("User creation failed", e);
        } finally {
            if (connection != null) {
                this.connectionManager.returnConnection(connection);
            }

        }

        return user;
    }

    public User getByID(long id) {
        Connection connection = this.connectionManager.getConnection();

        User user;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM USER WHERE ID = ?;");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            user = new User();
            user.setUuid(rs.getString("UID"));
            user.setId(rs.getLong("ID"));
            user.setUserName(rs.getString("username"));
            user.setPassword(rs.getString("password"));
        } catch (SQLException e) {
            LOG.error("Getting User by ID failed", e);
            throw new RepositoryException("Getting User by ID failed", e);
        } finally {
            if (connection != null) {
                this.connectionManager.returnConnection(connection);
            }

        }

        return user;
    }

    public void update(User user) {
        Connection connection = this.connectionManager.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO USER (UID,username,password) VALUES (?,?,?);");
            ps.setString(1, user.getUuid());
            ps.setString(2, user.getUserName());
            ps.setString(3, user.getPassword());
            ps.execute();
        } catch (SQLException e) {
            LOG.error("Updating user failed", e);
            throw new RepositoryException("Updating user failed", e);
        } finally {
            if (connection != null) {
                this.connectionManager.returnConnection(connection);
            }

        }

    }

    public void delete(User user) {
        Connection connection = this.connectionManager.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM USER WHERE UID=?;");
            System.out.println(user.getUuid());
            ps.setString(1, user.getUuid());
            if (ps.executeUpdate() == 0) {
                System.out.println("No rows found to delete.");
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

    public List<User> getAll() {
        return null;
    }

    public User getByUsername(String username) {
        Connection connection = this.connectionManager.getConnection();

        User user;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM USER WHERE username = ?;");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                rs.next();
                user = new User();
                user.setUuid(rs.getString("UID"));
                user.setId(rs.getLong("ID"));
                user.setUserName(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                return user;
            }

        } catch (SQLException var10) {
            LOG.error("Getting User by Name failed", var10);
            throw new RepositoryException("Getting User by Name failed", var10);
        } finally {
            if (connection != null) {
                this.connectionManager.returnConnection(connection);
            }

        }

        return null;
    }
}
