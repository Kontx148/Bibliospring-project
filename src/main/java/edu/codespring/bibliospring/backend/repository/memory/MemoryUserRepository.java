package edu.codespring.bibliospring.backend.repository.memory;

import edu.codespring.bibliospring.backend.model.User;
import edu.codespring.bibliospring.backend.repository.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
@Repository
@Profile("memory")
public class MemoryUserRepository implements UserRepository {
    private ConcurrentHashMap<Long, User> users = new ConcurrentHashMap();
    private AtomicLong idGenerator = new AtomicLong();

    public User create(User user) {
        user.setId(this.idGenerator.incrementAndGet());
        this.users.put(user.getId(), user);
        return user;
    }

    public User getByID(long id) {
        return (User)this.users.get(id);
    }

    public void update(User user) {
        this.users.put(user.getId(), user);
    }

    public void delete(User user) {
        this.users.remove(user.getId());
    }

    public List<User> getAll() {
        return new ArrayList(this.users.values());
    }

    public User getByUsername(String username) {
        return users.values().stream()
                .filter(u -> u.getUserName().equals(username))
                .findAny().orElse(null);
    }
}
