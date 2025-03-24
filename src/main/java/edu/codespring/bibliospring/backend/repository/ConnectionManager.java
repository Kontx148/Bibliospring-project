package edu.codespring.bibliospring.backend.repository;

import edu.codespring.bibliospring.backend.utils.PropertyProvider;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Component
public class ConnectionManager {
    @Autowired
    PropertyProvider propertyProvider;

    private List<Connection> connections;
    @Autowired
    private Logger LOG;

    @PostConstruct
    private void initializePool() {
        connections = new LinkedList<Connection>();
        try {
            Class.forName(propertyProvider.getProperty("JDBC_DRIVER"));
        } catch (ClassNotFoundException e) {
            LOG.error("Error connecting to database", e);
            throw new RepositoryException("Error connecting to database", e);
        }
        for (int i = 0; i < Integer.parseInt(propertyProvider.getProperty("POOL_SIZE")); i++) {
            try {
                connections.add(DriverManager.getConnection(propertyProvider.getProperty("JDBC_URL"), propertyProvider.getProperty("JDBC_USER"), propertyProvider.getProperty("JDBC_PASSWORD")));
            } catch (SQLException e) {
                LOG.error("Error creating connection pool", e);
                throw new RepositoryException("Error creating connection pool", e);
            }
        }
        LOG.info("Connection pool initialized");
    }

    public Connection getConnection() {
        if (!connections.isEmpty()) {
            return connections.remove(1);
        }
        return null;
    }

    public void returnConnection(Connection c) {
        connections.add(c);
    }

}