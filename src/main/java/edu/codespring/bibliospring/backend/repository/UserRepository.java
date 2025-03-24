package edu.codespring.bibliospring.backend.repository;

import edu.codespring.bibliospring.backend.model.User;
import java.util.List;

public interface UserRepository {
    User create(User var1);

    User getByID(long var1);

    void update(User var1);

    void delete(User var1);

    List<User> getAll();

    User getByUsername(String var1);
}
