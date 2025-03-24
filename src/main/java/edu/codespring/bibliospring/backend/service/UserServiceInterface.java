package edu.codespring.bibliospring.backend.service;

import edu.codespring.bibliospring.backend.model.User;

public interface UserServiceInterface {
    User register(User var1);

    boolean login(User var1);
}
