package org.example.service;

import org.example.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    User create(User user);
    Optional<User> findById(UUID id);
    User update(UUID id, User user);
    void delete(UUID id);
}
