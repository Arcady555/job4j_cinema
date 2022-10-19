package ru.job4j.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.model.User;
import ru.job4j.persistence.UserDbStore;

import java.util.Optional;

@Service
@ThreadSafe
public class UserService {
    private final UserDbStore userDbStore;

    public UserService(UserDbStore userDbStore) {
        this.userDbStore = userDbStore;
    }

    public Optional<User> add(User user) {
        return userDbStore.add(user);
    }

    public Optional<User> findByEmailAndPassword(String email, String password) {
        return userDbStore.findByEmailAndPassword(email, password);
    }
}