package edu.codespring.bibliospring.backend.service.impl;

import edu.codespring.bibliospring.backend.model.User;
import edu.codespring.bibliospring.backend.repository.RepositoryException;
import edu.codespring.bibliospring.backend.repository.UserRepository;
import edu.codespring.bibliospring.backend.service.ServiceException;
import edu.codespring.bibliospring.backend.service.UserServiceInterface;
import edu.codespring.bibliospring.backend.utils.PasswordEncrypter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Logger LOG;

    public User register(User user) {
        try {
            user.setPassword(PasswordEncrypter.generateHashPassword(user.getPassword(), user.getUuid()));
            return this.userRepository.create(user);
        } catch (RepositoryException var3) {
            LOG.error("Error registering user");
            throw new ServiceException("Error registering user");
        }
    }

    public boolean login(User user) {
        try {
            User dbUser = this.userRepository.getByUsername(user.getUserName());
            return dbUser != null ? dbUser.getPassword().equals(PasswordEncrypter.generateHashPassword(user.getPassword(), dbUser.getUuid())) : false;
        } catch (RepositoryException var3) {
            LOG.error("Error finding user");
            throw new ServiceException("Error finding user", var3);
        }
    }
}
