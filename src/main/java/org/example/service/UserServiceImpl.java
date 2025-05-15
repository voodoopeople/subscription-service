package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * Создает нового пользователя в системе.
     *
     * @param user объект User с email и именем
     * @return сохраненный пользователь с заполненным id и createdAt
     */
    @Override
    public User create(User user) {
        log.debug("Creating new user with email: {}", user.getEmail());
        User saved = userRepository.save(user);
        log.info("User created: {}", saved.getId());
        return saved;
    }

    /**
     * Поиск пользователя по идентификатору.
     *
     * @param id UUID пользователя
     * @return Optional с найденным пользователем или empty если не найден
     */
    @Override
    public Optional<User> findById(UUID id) {
        log.debug("Finding user by id: {}", id);
        Optional<User> opt = userRepository.findById(id);
        opt.ifPresentOrElse(
                u -> log.info("User found: {}", u.getEmail()),
                () -> log.warn("User not found: {}", id)
        );

        return opt;
    }

    /**
     * Обновляет данные существующего пользователя.
     *
     * @param id UUID существующего пользователя
     * @param user объект User с новыми email и/или именем
     * @return обновленный пользователь
     * @throws IllegalArgumentException если пользователь не найден
     */
    @Override
    public User update(UUID id, User user) {
        log.debug("Updating user {} with data: {}", id, user);
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
        User updated = User.builder()
                .id(existing.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
        User saved = userRepository.save(updated);
        log.info("User updated: {}", saved.getId());

        return saved;
    }

    /**
     * Удаляет пользователя по идентификатору.
     *
     * @param id UUID пользователя
     */
    @Override
    public void delete(UUID id) {
        log.debug("Deleting user: {}", id);
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            log.info("User deleted: {}", id);
        } else {
            log.warn("User to delete not found: {}", id);
        }
    }
}
